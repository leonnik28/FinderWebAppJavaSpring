package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.exception.EntityNotFoundException;
import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.service.DotaCharacterService;
import com.dota.personaji.dota2.model.Ability;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class CharacterController {

    private final DotaCharacterService characterService;
    private static final Logger logger = LoggerFactory.getLogger(CharacterController.class);


    private static final String NO_CHARACTERS_FOUND = "No characters found";

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
        return checkEntity(characterService.getAllCharacters(),
                NO_CHARACTERS_FOUND);
    }

    @GetMapping("/characters/{id}")
    public DotaCharacter getCharacterById(@PathVariable Long id) throws EntityNotFoundException {
        logAttempt("get character with id: " + id);
        DotaCharacter character = checkEntity(characterService.getCharacterById(id),
                "Character not found for id: " + id);
        logSuccess("retrieved character with id: " + id);
        return character;
    }

    @GetMapping("/characters/name")
    public List<DotaCharacter> getCharacterByName(@RequestParam String name)
            throws EntityNotFoundException {
        logAttempt("get characters with name: " + name);
        List<DotaCharacter> characters = checkEntity(characterService.getCharacterByName(name),
                NO_CHARACTERS_FOUND + " with name: " + name);
        logSuccess("retrieved characters with name: " + name);
        return characters;
    }

    @GetMapping("/characters/power")
    public List<DotaCharacter> getCharactersByPowerDesc() throws EntityNotFoundException {
        logAttempt("get characters by power desc");
        List<DotaCharacter> characters = checkEntity(characterService.getCharactersByPowerDesc(),
                NO_CHARACTERS_FOUND);
        logSuccess("retrieved characters by power desc");
        return characters;
    }

    @GetMapping("/characters/agility")
    public List<DotaCharacter> getCharactersByAgilityDesc() throws EntityNotFoundException {
        logAttempt("get characters by agility desc");
        List<DotaCharacter> characters = checkEntity(characterService.getCharactersByAgilityDesc(),
                NO_CHARACTERS_FOUND);
        logSuccess("retrieved characters by agility desc");
        return characters;
    }

    @GetMapping("/characters/intelligence")
    public List<DotaCharacter> getCharactersByIntelligenceDesc()
            throws EntityNotFoundException {
        logAttempt("get characters by intelligence desc");
        List<DotaCharacter> characters = checkEntity(
                characterService.getCharactersByIntelligenceDesc(),
                NO_CHARACTERS_FOUND);
        logSuccess("retrieved characters by intelligence desc");
        return characters;
    }

    @PostMapping("/characters/create")
    public DotaCharacter saveCharacter(@RequestBody DotaCharacter dotaCharacter)
            throws EntityNotFoundException {
        logAttempt("save character");
        List<Long> abilityIds = dotaCharacter.getAbilities().stream()
                .map(Ability::getId)
                .toList();
        DotaCharacter savedCharacter = checkEntity(characterService.saveCharacter(dotaCharacter,
                abilityIds), "Failed to save character");
        logSuccess("saved character");
        return savedCharacter;
    }

    @PutMapping("/characters/update")
    public DotaCharacter updateCharacter(@RequestParam Long id,
                                         @RequestBody DotaCharacter dotaCharacter)
            throws EntityNotFoundException {
        logAttempt("update character with id: " + id);
        List<Long> abilityIds = dotaCharacter.getAbilities().stream()
                .map(Ability::getId)
                .toList();
        DotaCharacter updatedCharacter =
                checkEntity(characterService.updateCharacter(id,
                                dotaCharacter,
                                abilityIds),
                        "Failed to update character with id: " + id);
        logSuccess("updated character with id: " + id);
        return updatedCharacter;
    }

    @PatchMapping("/characters/patch")
    public DotaCharacter patchCharacter(@RequestParam Long id,
                                        @RequestBody DotaCharacter dotaCharacter,
                                        @RequestParam(required = false) List<Long> abilityIds)
            throws EntityNotFoundException {
        logAttempt("patch character with id: " + id);
        DotaCharacter patchedCharacter =
                checkEntity(characterService.patchCharacter(id,
                dotaCharacter,
                abilityIds),
                "Failed to patch character with id: " + id);
        logSuccess("patched character with id: " + id);
        return patchedCharacter;
    }

    @PatchMapping("/characters/add/abilities/{id}")
    public DotaCharacter addAbilitiesToCharacter(@PathVariable Long id,
                                                 @RequestParam List<Long> abilityIds)
            throws EntityNotFoundException {
        logAttempt("add abilities to character with id: " + id);
        DotaCharacter updatedCharacter =
                checkEntity(characterService.addAbilitiesToCharacter(id, abilityIds),
                        "Failed to add abilities to character with id: " + id);
        logSuccess("added abilities to character with id: " + id);
        return updatedCharacter;
    }

    @DeleteMapping("/characters/delete/abilities/{id}")
    public DotaCharacter removeAbilitiesFromCharacter(@PathVariable Long id,
                                                      @RequestParam List<Long> abilityIds)
            throws EntityNotFoundException {
        logAttempt("remove abilities from character with id: " + id);
        DotaCharacter updatedCharacter =
                checkEntity(characterService.removeAbilitiesFromCharacter(id,
                abilityIds),
                "Failed to remove abilities from character with id: " + id);
        logSuccess("removed abilities from character with id: " + id);
        return updatedCharacter;
    }

    @GetMapping("/characters/strong/{power}")
    public List<DotaCharacter> getStrongCharacters(@PathVariable int power) {
        logAttempt("get strong characters with power: " + power);
        List<DotaCharacter> characters = characterService.getStrongCharacters(power);
        if (characters.isEmpty()) {
            throw new EntityNotFoundException(NO_CHARACTERS_FOUND + " with power: " + power);
        }
        logSuccess("retrieved strong characters with power: " + power);
        return characters;
    }

    @DeleteMapping("/characters/delete")
    public String deleteCharacter(@RequestParam("id") Long id) {
        logAttempt("delete character with id: " + id);

        DotaCharacter character = characterService.getCharacterById(id);
        if (character == null) {
            throw new EntityNotFoundException("Character not found for this id :: "
                    + id);
        }

        characterService.deleteCharacter(id);
        logSuccess("deleted character with id: " + id);
        return "Deleted character id - " + id;
    }

    @PostMapping("/characters/bulkCreate")
    public ResponseEntity<Object> createBulkCharacters(
            @RequestBody List<DotaCharacter> dotaCharacters) {
        logAttempt("create multiple characters");
        List<DotaCharacter> createdCharacters =
                characterService.createBulkCharacters(dotaCharacters);
        if (createdCharacters.isEmpty()) {
            throw new EntityNotFoundException("No characters created");
        }
        logSuccess("created multiple characters");
        return new ResponseEntity<>(createdCharacters, HttpStatus.OK);
    }

    @DeleteMapping("/characters/bulkDelete")
    public ResponseEntity<Object> deleteBulkCharacters(
            @RequestBody List<DotaCharacter> dotaCharacters) {
        logAttempt("delete multiple characters");
        characterService.deleteBulkCharacters(dotaCharacters);
        logSuccess("deleted multiple characters");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
