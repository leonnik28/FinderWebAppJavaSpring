package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.controller.exeption.EntityNotFoundException;
import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.service.DotaCharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CharacterController {

    private final DotaCharacterService characterService;
    private static final Logger logger = LoggerFactory.getLogger(CharacterController.class);

    @Autowired
    public CharacterController(DotaCharacterService characterService) {
        this.characterService = characterService;
    }

    private void logAttempt(String action) {
        logger.info("Attempting to {}", action);
    }

    private void logSuccess(String action) {
        logger.info("Successfully {}", action);
    }


    private <T> T checkEntity(T entity, String errorMessage) throws EntityNotFoundException {
        if (entity == null) {
            throw new EntityNotFoundException(errorMessage);
        }
        return entity;
    }

    @GetMapping("/characters")
    public List<DotaCharacter> getAllCharacters() throws EntityNotFoundException {
        logAttempt("get all characters");
        List<DotaCharacter> characters = checkEntity(characterService.getAllCharacters(), "No characters found");
        logSuccess("retrieved all characters");
        return characters;
    }

    @GetMapping("/characters/{id}")
    public DotaCharacter getCharacterById(@PathVariable Long id) throws EntityNotFoundException {
        logAttempt("get character with id: " + id);
        DotaCharacter character = checkEntity(characterService.getCharacterById(id), "Character not found for id: " + id);
        logSuccess("retrieved character with id: " + id);
        return character;
    }

    @GetMapping("/characters/name/{name}")
    public List<DotaCharacter> getCharacterByName(@PathVariable String name) throws EntityNotFoundException {
        logAttempt("get characters with name: " + name);
        List<DotaCharacter> characters = checkEntity(characterService.getCharacterByName(name), "No characters found with name: " + name);
        logSuccess("retrieved characters with name: " + name);
        return characters;
    }

    @GetMapping("/characters/power")
    public List<DotaCharacter> getCharactersByPowerDesc() throws EntityNotFoundException {
        logAttempt("get characters by power desc");
        List<DotaCharacter> characters = checkEntity(characterService.getCharactersByPowerDesc(), "No characters found");
        logSuccess("retrieved characters by power desc");
        return characters;
    }

    @GetMapping("/characters/agility")
    public List<DotaCharacter> getCharactersByAgilityDesc() throws EntityNotFoundException {
        logAttempt("get characters by agility desc");
        List<DotaCharacter> characters = checkEntity(characterService.getCharactersByAgilityDesc(), "No characters found");
        logSuccess("retrieved characters by agility desc");
        return characters;
    }

    @GetMapping("/characters/intelligence")
    public List<DotaCharacter> getCharactersByIntelligenceDesc() throws EntityNotFoundException {
        logAttempt("get characters by intelligence desc");
        List<DotaCharacter> characters = checkEntity(characterService.getCharactersByIntelligenceDesc(), "No characters found");
        logSuccess("retrieved characters by intelligence desc");
        return characters;
    }

    @PostMapping("/characters/create")
    public DotaCharacter saveCharacter(@RequestBody DotaCharacter dotaCharacter, @RequestParam List<Long> abilityIds) throws EntityNotFoundException {
        logAttempt("save character");
        DotaCharacter savedCharacter = checkEntity(characterService.saveCharacter(dotaCharacter, abilityIds), "Failed to save character");
        logSuccess("saved character");
        return savedCharacter;
    }

    @PutMapping("/characters/update/{id}")
    public DotaCharacter updateCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter, @RequestParam List<Long> abilityIds) throws EntityNotFoundException {
        logAttempt("update character with id: " + id);
        DotaCharacter updatedCharacter = checkEntity(characterService.updateCharacter(id, dotaCharacter, abilityIds), "Failed to update character with id: " + id);
        logSuccess("updated character with id: " + id);
        return updatedCharacter;
    }

    @PatchMapping("/characters/patch/{id}")
    public DotaCharacter patchCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter, @RequestParam(required = false) List<Long> abilityIds) throws EntityNotFoundException {
        logAttempt("patch character with id: " + id);
        DotaCharacter patchedCharacter = checkEntity(characterService.patchCharacter(id, dotaCharacter, abilityIds), "Failed to patch character with id: " + id);
        logSuccess("patched character with id: " + id);
        return patchedCharacter;
    }

    @DeleteMapping("/characters/delete/abilities/{id}")
    public DotaCharacter removeAbilitiesFromCharacter(@PathVariable Long id, @RequestParam List<Long> abilityIds) throws EntityNotFoundException {
        logAttempt("remove abilities from character with id: " + id);
        DotaCharacter updatedCharacter = checkEntity(characterService.removeAbilitiesFromCharacter(id, abilityIds), "Failed to remove abilities from character with id: " + id);
        logSuccess("removed abilities from character with id: " + id);
        return updatedCharacter;
    }

    @GetMapping("/characters/strong/{power}")
    public List<DotaCharacter> getStrongCharacters(@PathVariable int power) throws EntityNotFoundException {
        logAttempt("get strong characters with power: " + power);
        List<DotaCharacter> characters = checkEntity(characterService.getStrongCharacters(power), "No characters found with power: " + power);
        logSuccess("retrieved strong characters with power: " + power);
        return characters;
    }

    @DeleteMapping("/characters/delete/{id}")
    public String deleteCharacter(@PathVariable Long id) throws EntityNotFoundException {
        logAttempt("delete character with id: " + id);
        characterService.deleteCharacter(id);
        logSuccess("deleted character with id: " + id);
        return "Deleted character id - " + id;
    }
}
