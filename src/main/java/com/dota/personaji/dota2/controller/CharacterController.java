package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.service.DotaCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CharacterController {

    private final DotaCharacterService characterService;

    @Autowired
    public CharacterController(DotaCharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/characters")
    public ResponseEntity<Object> getAllCharacters() {
        List<DotaCharacter> characters = characterService.getAllCharacters();
        if (characters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No characters found");
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<Object> getCharacterById(@PathVariable Long id) {
        DotaCharacter character = characterService.getCharacterById(id);
        if (character == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character not found for id: " + id);
        }
        return ResponseEntity.ok(character);
    }

    @GetMapping("/characters/name/{name}")
    public ResponseEntity<Object> getCharacterByName(@PathVariable String name) {
        List<DotaCharacter> characters = characterService.getCharacterByName(name);
        if (characters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No characters found with name: " + name);
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/characters/power")
    public ResponseEntity<Object> getCharactersByPowerDesc() {
        List<DotaCharacter> characters = characterService.getCharactersByPowerDesc();
        if (characters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No characters found");
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/characters/agility")
    public ResponseEntity<Object> getCharactersByAgilityDesc() {
        List<DotaCharacter> characters = characterService.getCharactersByAgilityDesc();
        if (characters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No characters found");
        }
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/characters/intelligence")
    public ResponseEntity<Object> getCharactersByIntelligenceDesc() {
        List<DotaCharacter> characters = characterService.getCharactersByIntelligenceDesc();
        if (characters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No characters found");
        }
        return ResponseEntity.ok(characters);
    }

    @PostMapping("/characters/create")
    public ResponseEntity<Object> saveCharacter(@RequestBody DotaCharacter dotaCharacter, @RequestParam List<Long> abilityIds) {
        DotaCharacter savedCharacter = characterService.saveCharacter(dotaCharacter, abilityIds);
        if (savedCharacter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to save character");
        }
        return ResponseEntity.ok(savedCharacter);
    }

    @PutMapping("/characters/update/{id}")
    public ResponseEntity<Object> updateCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter, @RequestParam List<Long> abilityIds) {
        DotaCharacter updatedCharacter = characterService.updateCharacter(id, dotaCharacter, abilityIds);
        if (updatedCharacter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update character with id: " + id);
        }
        return ResponseEntity.ok(updatedCharacter);
    }

    @PatchMapping("/characters/patch/{id}")
    public ResponseEntity<Object> patchCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter, @RequestParam(required = false) List<Long> abilityIds) {
        DotaCharacter patchedCharacter = characterService.patchCharacter(id, dotaCharacter, abilityIds);
        if (patchedCharacter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to patch character with id: " + id);
        }
        return ResponseEntity.ok(patchedCharacter);
    }

    @PatchMapping("/characters/patch/abilities/{id}")
    public ResponseEntity<Object> patchCharacterAbilities(@PathVariable Long id, @RequestParam(required = false) List<Long> abilityIds) {
        DotaCharacter patchedCharacter = characterService.addAbilitiesToCharacter(id, abilityIds);
        if (patchedCharacter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to patch abilities for character with id: " + id);
        }
        return ResponseEntity.ok(patchedCharacter);
    }

    @DeleteMapping("/characters/delete/abilities/{id}")
    public ResponseEntity<Object> removeAbilitiesFromCharacter(@PathVariable Long id, @RequestParam List<Long> abilityIds) {
        DotaCharacter updatedCharacter = characterService.removeAbilitiesFromCharacter(id, abilityIds);
        if (updatedCharacter == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to remove abilities from character with id: " + id);
        }
        return ResponseEntity.ok(updatedCharacter);
    }

    @GetMapping("/characters/strong/{power}")
    public ResponseEntity<Object> getStrongCharacters(@PathVariable int power) {
        List<DotaCharacter> characters = characterService.getStrongCharacters(power);
        if (characters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No characters found with power: " + power);
        }
        return ResponseEntity.ok(characters);
    }

    @DeleteMapping("/characters/delete/{id}")
    public ResponseEntity<String> deleteCharacter(@PathVariable Long id) {
        try {
            characterService.deleteCharacter(id);
            return ResponseEntity.ok("Deleted character id - " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete character with id: " + id);
        }
    }
}
