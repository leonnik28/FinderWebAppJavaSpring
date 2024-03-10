package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.service.DotaCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<DotaCharacter> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @GetMapping("/characters/{id}")
    public DotaCharacter getCharacterById(@PathVariable Long id) {
        return characterService.getCharacterById(id);
    }

    @GetMapping("/characters/name/{name}")
    public List<DotaCharacter> getCharacterByName(@PathVariable String name) {
        return characterService.getCharacterByName(name);
    }

    @GetMapping("/characters/power")
    public List<DotaCharacter> getCharactersByPowerDesc() {
        return characterService.getCharactersByPowerDesc();
    }

    @GetMapping("/characters/agility")
    public List<DotaCharacter> getCharactersByAgilityDesc() {
        return characterService.getCharactersByAgilityDesc();
    }

    @GetMapping("/characters/intelligence")
    public List<DotaCharacter> getCharactersByIntelligenceDesc() {
        return characterService.getCharactersByIntelligenceDesc();
    }

    @PostMapping("/characters/create")
    public DotaCharacter saveCharacter(@RequestBody DotaCharacter dotaCharacter, @RequestParam List<Long> abilityIds) {
        return characterService.saveCharacter(dotaCharacter, abilityIds);
    }

    @PutMapping("/characters/update/{id}")
    public DotaCharacter updateCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter, @RequestParam List<Long> abilityIds) {
        return characterService.updateCharacter(id, dotaCharacter, abilityIds);
    }

    @PatchMapping("/characters/patch/{id}")
    public DotaCharacter patchCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter, @RequestParam(required = false) List<Long> abilityIds) {
        return characterService.patchCharacter(id, dotaCharacter, abilityIds);
    }

    @DeleteMapping("/characters/delete/{id}")
    public String deleteCharacter(@PathVariable Long id) {
        characterService.deleteCharacter(id);
        return "Deleted character id - " + id;
    }
}
