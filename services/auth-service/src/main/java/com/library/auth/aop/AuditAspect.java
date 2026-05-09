package com.library.auth.aop;

import com.library.auth.entity.AuditLog;
import com.library.auth.repository.AuditLogRepository;
import com.library.auth.security.UserPrincipal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Aspect
@Component
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    private final AuditLogRepository auditLogRepository;

    public AuditAspect(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @AfterReturning("@annotation(auditable)")
    public void writeAuditLog(JoinPoint joinPoint, Auditable auditable) {
        try {
            UUID actorUserId = extractCurrentUserId().orElse(null);
            String entityId = extractEntityId(joinPoint).orElse(null);

            AuditLog auditLog = new AuditLog(
                    actorUserId,
                    auditable.action(),
                    auditable.entityName(),
                    entityId,
                    buildDetails(joinPoint)
            );

            auditLogRepository.save(auditLog);
        } catch (Exception exception) {
            log.warn("Failed to write audit log: {}", exception.getMessage());
        }
    }

    private Optional<UUID> extractCurrentUserId() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            return Optional.empty();
        }

        return Optional.of(principal.getId());
    }

    private Optional<String> extractEntityId(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs())
                .filter(UUID.class::isInstance)
                .map(UUID.class::cast)
                .findFirst()
                .map(UUID::toString);
    }

    private String buildDetails(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        return "Executed " + className + "." + methodName;
    }
}