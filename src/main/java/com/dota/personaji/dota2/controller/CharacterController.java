package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.service.DotaCharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/characters")
    public List<DotaCharacter> getAllCharacters()
            throws Exception {
        logger.info("Attempting to get all characters");
        List<DotaCharacter> characters = characterService.getAllCharacters();
        if (characters.isEmpty()) {
            throw new Exception("No characters found");
        }
        logger.info("Successfully retrieved all characters");
        return characters;
    }

    @GetMapping("/characters/{id}")
    public DotaCharacter getCharacterById(@PathVariable Long id)
            throws Exception {
        logger.info("Attempting to get character with id: " + id);
        DotaCharacter character = characterService.getCharacterById(id);
        if (character == null) {
            throw new Exception("Character not found for id: " + id);
        }
        logger.info("Successfully retrieved character with id: " + id);
        return character;
    }

    @GetMapping("/characters/name/{name}")
    public List<DotaCharacter> getCharacterByName(@PathVariable String name)
            throws Exception {
        logger.info("Attempting to get characters with name: " + name);
        List<DotaCharacter> characters = characterService.getCharacterByName(name);
        if (characters.isEmpty()) {
            throw new Exception("No characters found with name: " + name);
        }
        logger.info("Successfully retrieved characters with name: " + name);
        return characters;
    }

    @GetMapping("/characters/power")
    public List<DotaCharacter> getCharactersByPowerDesc()
            throws Exception {
        logger.info("Attempting to get characters by power desc");
        List<DotaCharacter> characters = characterService.getCharactersByPowerDesc();
        if (characters.isEmpty()) {
            throw new Exception("No characters found");
        }
        logger.info("Successfully retrieved characters by power desc");
        return characters;
    }

    @GetMapping("/characters/agility")
    public List<DotaCharacter> getCharactersByAgilityDesc()
            throws Exception {
        logger.info("Attempting to get characters by agility desc");
        List<DotaCharacter> characters = characterService.getCharactersByAgilityDesc();
        if (characters.isEmpty()) {
            throw new Exception("No characters found");
        }
        logger.info("Successfully retrieved characters by agility desc");
        return characters;
    }

    @GetMapping("/characters/intelligence")
    public List<DotaCharacter> getCharactersByIntelligenceDesc()
            throws Exception {
        logger.info("Attempting to get characters by intelligence desc");
        List<DotaCharacter> characters = characterService.getCharactersByIntelligenceDesc();
        if (characters.isEmpty()) {
            throw new Exception("No characters found");
        }
        logger.info("Successfully retrieved characters by intelligence desc");
        return characters;
    }

    @PostMapping("/characters/create")
    public DotaCharacter saveCharacter(@RequestBody DotaCharacter dotaCharacter, @RequestParam List<Long> abilityIds)
            throws Exception {
        logger.info("Attempting to save character");
        DotaCharacter savedCharacter = characterService.saveCharacter(dotaCharacter, abilityIds);
        if (savedCharacter == null) {
            throw new Exception("Failed to save character");
        }
        logger.info("Successfully saved character");
        return savedCharacter;
    }

    @PutMapping("/characters/update/{id}")
    public DotaCharacter updateCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter, @RequestParam List<Long> abilityIds)
            throws Exception {
        logger.info("Attempting to update character with id: " + id);
        DotaCharacter updatedCharacter = characterService.updateCharacter(id, dotaCharacter, abilityIds);
        if (updatedCharacter == null) {
            throw new Exception("Failed to update character with id: " + id);
        }
        logger.info("Successfully updated character with id: " + id);
        return updatedCharacter;
    }

    @PatchMapping("/characters/patch/{id}")
    public DotaCharacter patchCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter, @RequestParam(required = false) List<Long> abilityIds)
            throws Exception {
        logger.info("Attempting to patch character with id: " + id);
        DotaCharacter patchedCharacter = characterService.patchCharacter(id, dotaCharacter, abilityIds);
        if (patchedCharacter == null) {
            throw new Exception("Failed to patch character with id: " + id);
        }
        logger.info("Successfully patched character with id: " + id);
        return patchedCharacter;
    }

    @PatchMapping("/characters/patch/abilities/{id}")
    public DotaCharacter patchCharacterAbilities(@PathVariable Long id, @RequestParam(required = false) List<Long> abilityIds) throws Exception {
        logger.info("Attempting to patch abilities for character with id: " + id);
        if (abilityIds == null) {
            throw new Exception("Ability IDs must not be null");
        }
        DotaCharacter patchedCharacter = characterService.addAbilitiesToCharacter(id, abilityIds);
        if (patchedCharacter == null) {
            throw new Exception("Failed to patch abilities for character with id: " + id);
        }
        logger.info("Successfully patched abilities for character with id: " + id);
        return patchedCharacter;
    }

    @DeleteMapping("/characters/delete/abilities/{id}")
    public DotaCharacter removeAbilitiesFromCharacter(@PathVariable Long id, @RequestParam List<Long> abilityIds)
            throws Exception {
        logger.info("Attempting to remove abilities from character with id: " + id);
        DotaCharacter updatedCharacter = characterService.removeAbilitiesFromCharacter(id, abilityIds);
        if (updatedCharacter == null) {
            throw new Exception("Failed to remove abilities from character with id: " + id);
        }
        logger.info("Successfully removed abilities from character with id: " + id);
        return updatedCharacter;
    }

    @GetMapping("/characters/strong/{power}")
    public List<DotaCharacter> getStrongCharacters(@PathVariable int power)
            throws Exception {
        logger.info("Attempting to get strong characters with power: " + power);
        List<DotaCharacter> characters = characterService.getStrongCharacters(power);
        if (characters.isEmpty()) {
            throw new Exception("No characters found with power: " + power);
        }
        logger.info("Successfully retrieved strong characters with power: " + power);
        return characters;
    }

    @DeleteMapping("/characters/delete/{id}")
    public String deleteCharacter(@PathVariable Long id)
            throws Exception {
        logger.info("Attempting to delete character with id: " + id);
        characterService.deleteCharacter(id);
        logger.info("Successfully deleted character with id: " + id);
        return "Deleted character id - " + id;
    }
}
