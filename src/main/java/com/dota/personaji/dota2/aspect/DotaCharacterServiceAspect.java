package com.dota.personaji.dota2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DotaCharacterServiceAspect {

    private static final Logger logger =
            LoggerFactory.getLogger(DotaCharacterServiceAspect.class);

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService.getAllCharacters(..))")
    public Object logGetAllCharacters(ProceedingJoinPoint joinPoint)
            throws Throwable {
        logger.info("Retrieving all characters...");
        Object result = joinPoint.proceed();
        logger.info("Characters retrieved successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService.getCharacterById(..))")
    public Object logGetCharacterById(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Retrieving character for id '{}'...", id);
        Object result = joinPoint.proceed();
        if (result == null) {
            logger.info("Character not found for id '{}'.", id);
        } else {
            logger.info("Character retrieved successfully.");
        }
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService.getCharacterByName(..))")
    public Object logGetCharacterByName(ProceedingJoinPoint joinPoint)
            throws Throwable {
        String name = (String) joinPoint.getArgs()[0];
        logger.info("Retrieving character by name '{}'...", name);
        Object result = joinPoint.proceed();
        logger.info("Character retrieved successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService"
            + ".getCharactersByPowerDesc(..))")
    public Object logGetCharactersByPowerDesc(ProceedingJoinPoint joinPoint)
            throws Throwable {
        logger.info("Retrieving characters by power in descending order...");
        Object result = joinPoint.proceed();
        logger.info("Characters retrieved successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService"
            + ".getCharactersByAgilityDesc(..))")
    public Object logGetCharactersByAgilityDesc(ProceedingJoinPoint joinPoint)
            throws Throwable {
        logger.info("Retrieving characters by agility in descending order...");
        Object result = joinPoint.proceed();
        logger.info("Characters retrieved successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService"
            + ".getCharactersByIntelligenceDesc(..))")
    public Object logGetCharactersByIntelligenceDesc(ProceedingJoinPoint joinPoint)
            throws Throwable {
        logger.info("Retrieving characters by intelligence in descending order...");
        Object result = joinPoint.proceed();
        logger.info("Characters retrieved successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService.saveCharacter(..))")
    public Object logSaveCharacter(ProceedingJoinPoint joinPoint)
            throws Throwable {
        logger.info("Saving character...");
        Object result = joinPoint.proceed();
        logger.info("Character saved successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService.updateCharacter(..))")
    public Object logUpdateCharacter(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Updating character for id '{}'...", id);
        Object result = joinPoint.proceed();
        logger.info("Character updated successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService.patchCharacter(..))")
    public Object logPatchCharacter(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Patching character for id '{}'...", id);
        Object result = joinPoint.proceed();
        logger.info("Character patched successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService"
            + ".addAbilitiesToCharacter(..))")
    public Object logAddAbilitiesToCharacter(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Adding abilities to character for id '{}'...", id);
        Object result = joinPoint.proceed();
        logger.info("Abilities added successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService"
            + ".removeAbilitiesFromCharacter(..))")
    public Object logRemoveAbilitiesFromCharacter(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Removing abilities from character for id '{}'...", id);
        Object result = joinPoint.proceed();
        logger.info("Abilities removed successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService.getStrongCharacters(..))")
    public Object logGetStrongCharacters(ProceedingJoinPoint joinPoint)
            throws Throwable {
        int power = (int) joinPoint.getArgs()[0];
        logger.info("Retrieving strong characters with power greater than '{}'...", power);
        Object result = joinPoint.proceed();
        logger.info("Characters retrieved successfully.");
        return result;
    }

    @Around("execution"
            + "(* com.dota.personaji.dota2.service.DotaCharacterService.deleteCharacter(..))")
    public Object logDeleteCharacter(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Long id = (Long) joinPoint.getArgs()[0];
        logger.info("Deleting character for id '{}'...", id);
        Object result = joinPoint.proceed();
        logger.info("Character deleted successfully.");
        return result;
    }
}
