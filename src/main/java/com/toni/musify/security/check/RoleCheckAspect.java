package com.toni.musify.security.check;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;

import static com.toni.musify.security.ApplicationConfig.getCurrentUserRole;

@Aspect
@Component
public class RoleCheckAspect {

    @Before("@annotation(RolesAllowed)")
    public void before(JoinPoint joinPoint) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        String[] expectedRoles = ms.getMethod().getAnnotation(RolesAllowed.class).value();

        if(Arrays.stream(expectedRoles).noneMatch(role->role.equals(getCurrentUserRole())))
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "NO ACCESS!");
    }
}
