package com.library.auth.service.impl;

import com.library.auth.dto.request.LoginRequest;
import com.library.auth.dto.request.RegisterRequest;
import com.library.auth.dto.response.AuthResponse;
import com.library.auth.dto.response.UserResponse;
import com.library.auth.entity.Role;
import com.library.auth.entity.User;
import com.library.auth.enums.RoleName;
import com.library.auth.mapper.UserMapper;
import com.library.auth.repository.RoleRepository;
import com.library.auth.repository.UserRepository;
import com.library.auth.security.JwtService;
import com.library.auth.service.AuthService;
import com.library.common.exception.ConflictException;
import com.library.common.exception.ForbiddenException;
import com.library.common.exception.NotFoundException;
import com.library.common.exception.UnauthorizedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        String email = normalizeEmail(request.email());

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ConflictException("Email already exists");
        }

        Role defaultRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new NotFoundException("Default user role is not configured"));

        User user = new User(
                request.fullName().trim(),
                email,
                normalizePhone(request.phone()),
                passwordEncoder.encode(request.password()),
                defaultRole
        );

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);
        UserResponse userResponse = userMapper.toResponse(savedUser);

        return AuthResponse.bearer(
                token,
                jwtService.expirationMinutes(),
                userResponse
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String email = normalizeEmail(request.email());

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!user.isActive()) {
            throw new ForbiddenException("User account is disabled");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            request.password()
                    )
            );
        } catch (BadCredentialsException exception) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);
        UserResponse userResponse = userMapper.toResponse(user);

        return AuthResponse.bearer(
                token,
                jwtService.expirationMinutes(),
                userResponse
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUserProfile(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            return null;
        }

        return phone.trim();
    }
}