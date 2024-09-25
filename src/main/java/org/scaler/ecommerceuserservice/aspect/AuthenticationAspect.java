package org.scaler.ecommerceuserservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @author: Vijaysurya Mandala
 * @github: github/mandalavijaysurya (<a href="https://www.github.com/mandalavijaysurya"> Github</a>)
 */
//@Aspect
//@Component
public class AuthenticationAspect {
    private static final Logger logger = Logger.getLogger("AuthenticationLogger");

    @Pointcut("execution(* org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider.authenticate(..))")
    public void authenticateMethod() {}

    @Before("authenticateMethod()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before method: " + joinPoint.getSignature().getName());
    }

    @After("authenticateMethod()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("After method: " + joinPoint.getSignature().getName());
    }
}
