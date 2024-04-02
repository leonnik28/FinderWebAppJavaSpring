package com.dota.personaji.dota2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AbilityServiceAspect {

    private static final Logger logger = LoggerFactory.getLogger(AbilityServiceAspect.class);

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.AbilityService.getAllAbilities(..))")
    public Object logGetAllAbilities(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Retrieving all abilities...");
        Object result = joinPoint.proceed();
        logger.info("Abilities retrieved successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.AbilityService.getAbilityById(..))")
    public Object logGetAbilityById(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Retrieving ability for id '{}'...", id);
        Object result = joinPoint.proceed();
        if (result == null) {
            logger.info("Ability not found for id '{}'.", id);
        } else {
            logger.info("Ability retrieved successfully.");
        }
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.AbilityService.saveAbility(..))")
    public Object logSaveAbility(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Saving ability...");
        Object result = joinPoint.proceed();
        logger.info("Ability saved successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.AbilityService.updateAbility(..))")
    public Object logUpdateAbility(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Updating ability for id '{}'...", id);
        Object result = joinPoint.proceed();
        logger.info("Ability updated successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.AbilityService.patchAbility(..))")
    public Object logPatchAbility(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Patching ability for id '{}'...", id);
        Object result = joinPoint.proceed();
        logger.info("Ability patched successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.AbilityService.deleteAbility(..))")
    public Object logDeleteAbility(ProceedingJoinPoint joinPoint) throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Deleting ability for id '{}'...", id);
        Object result = joinPoint.proceed();
        logger.info("Ability deleted successfully.");
        return result;
    }
}
