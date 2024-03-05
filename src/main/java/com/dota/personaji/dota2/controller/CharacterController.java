package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.model.Ability;
import com.dota.personaji.dota2.service.DotaCharacterService;
import com.dota.personaji.dota2.model.Role;
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

    @GetMapping("/characters/{id}/abilities")
    public List<Ability> getAbilitiesByCharacterId(@PathVariable Long id) {
        return characterService.getAbilitiesByCharacterId(id);
    }

    @GetMapping("/characters/name/{characterName}/abilities")
    public List<Ability> getAbilitiesByCharacterName(@PathVariable String characterName) {
        return characterService.getAbilitiesByCharacterName(characterName);
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

    @GetMapping("/characters/name/{characterName}/roles")
    public List<Role> getRolesByCharacterName(@PathVariable String characterName) {
        return characterService.getRolesByCharacterName(characterName);
    }

    @PostMapping("/characters/create")
    public DotaCharacter saveCharacter(@RequestBody DotaCharacter dotaCharacter) {
        characterService.saveCharacter(dotaCharacter);
        return dotaCharacter;
    }

    @PutMapping("/characters/{characterId}/add/{abilityId}")
    public DotaCharacter addAbilityToCharacter(@PathVariable Long abilityId, @PathVariable Long characterId) {
        return characterService.addAbilityToCharacter(characterId, abilityId);
    }

    @PutMapping("/characters/update/{id}")
    public DotaCharacter updateCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter) {
        return characterService.updateCharacter(id, dotaCharacter);
    }

    @PatchMapping("/characters/patch/{id}")
    public DotaCharacter patchCharacter(@PathVariable Long id, @RequestBody DotaCharacter dotaCharacter) {
        return characterService.patchCharacter(id, dotaCharacter);
    }

    @DeleteMapping("/characters/delete/{id}")
    public String deleteCharacter(@PathVariable Long id) {
        characterService.deleteCharacter(id);
        return "Deleted character id - " + id;
    }
}
