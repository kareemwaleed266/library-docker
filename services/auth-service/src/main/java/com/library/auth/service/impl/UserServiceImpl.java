package com.library.auth.service.impl;

import com.library.auth.dto.request.UpdateRoleRequest;
import com.library.auth.dto.response.UserResponse;
import com.library.auth.entity.Role;
import com.library.auth.entity.User;
import com.library.auth.enums.RoleName;
import com.library.auth.mapper.UserMapper;
import com.library.auth.repository.RoleRepository;
import com.library.auth.repository.UserRepository;
import com.library.auth.service.UserService;
import com.library.common.dto.PageResponse;
import com.library.common.exception.BadRequestException;
import com.library.common.exception.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.library.auth.aop.Auditable;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getUsers(Pageable pageable) {
        return PageResponse.from(
                userRepository.findAll(pageable)
                        .map(userMapper::toResponse)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID userId) {
        User user = findUserById(userId);
        return userMapper.toResponse(user);
    }

    @Override
    @Auditable(action = "UPDATE_USER_ROLE", entityName = "User")
    public UserResponse updateUserRole(UUID userId, UpdateRoleRequest request) {
        User user = findUserById(userId);
        RoleName currentRole = user.getRole().getName();
        RoleName requestedRole = request.role();

        if (currentRole == requestedRole) {
            return userMapper.toResponse(user);
        }

        if (isChangingLastActiveAdmin(user, requestedRole)) {
            throw new BadRequestException("Cannot change the role of the last active admin");
        }

        Role newRole = roleRepository.findByName(requestedRole)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        user.setRole(newRole);

        return userMapper.toResponse(user);
    }

    @Override
    @Auditable(action = "ACTIVATE_USER", entityName = "User")
    public UserResponse activateUser(UUID userId) {
        User user = findUserById(userId);

        if (!user.isActive()) {
            user.activate();
        }

        return userMapper.toResponse(user);
    }

    @Override
    @Auditable(action = "DEACTIVATE_USER", entityName = "User")
    public UserResponse deactivateUser(UUID userId) {
        User user = findUserById(userId);

        if (!user.isActive()) {
            return userMapper.toResponse(user);
        }

        if (isLastActiveAdmin(user)) {
            throw new BadRequestException("Cannot deactivate the last active admin");
        }

        user.deactivate();

        return userMapper.toResponse(user);
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private boolean isChangingLastActiveAdmin(User user, RoleName requestedRole) {
        return user.isActive()
                && user.getRole().getName() == RoleName.ADMIN
                && requestedRole != RoleName.ADMIN
                && userRepository.countActiveUsersByRole(RoleName.ADMIN) <= 1;
    }

    private boolean isLastActiveAdmin(User user) {
        return user.isActive()
                && user.getRole().getName() == RoleName.ADMIN
                && userRepository.countActiveUsersByRole(RoleName.ADMIN) <= 1;
    }
}