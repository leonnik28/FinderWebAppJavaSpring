package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.model.Ability;
import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.service.AbilityService;
import java.util.List;

import com.dota.personaji.dota2.service.DotaCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://web-app-by-java-frontend.vercel.app/", "https://finderwebappjavaspring.onrender.com"})
@RequestMapping("/api")
public class AbilityController {

    private final AbilityService abilityService;

    private final DotaCharacterService dotaCharacterService;

    @Autowired
    public AbilityController(AbilityService abilityService, DotaCharacterService dotaCharacterService) {
        this.abilityService = abilityService;
        this.dotaCharacterService = dotaCharacterService;
    }

    @GetMapping("/abilities")
    public ResponseEntity<Object> getAbilities() {
        List<Ability> abilities = abilityService.getAllAbilities();
        if (abilities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No abilities found");
        }
        return ResponseEntity.ok(abilities);
    }

    @GetMapping("/abilities/name/character")
    public DotaCharacter getCharacterByAbilityName(@RequestParam("name") String name) {
        Ability ability = abilityService.getAbilityByName(name);
        return dotaCharacterService.getCharacterByAbility(ability);
    }

    @GetMapping("/abilities/name")
    public Ability getAbilityByName(@RequestParam("name") String name) {
        return abilityService.getAbilityByName(name);
    }

    @GetMapping("/abilities/{id}")
    public ResponseEntity<Object> getAbility(@PathVariable Long id) {
        Ability ability = abilityService.getAbilityById(id);
        if (ability == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ability not found for id: " + id);
        }
        return ResponseEntity.ok(ability);
    }

    @PostMapping("/abilities/create")
    public ResponseEntity<Object> createAbility(@RequestBody Ability ability) {
        Ability createdAbility = abilityService.saveAbility(ability);
        if (createdAbility == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create ability");
        }
        return ResponseEntity.ok(createdAbility);
    }

    @PutMapping("/abilities/update/{id}")
    public ResponseEntity<Object> updateAbility(@PathVariable Long id,
                                                @RequestBody Ability ability) {
        Ability updatedAbility = abilityService.updateAbility(id, ability);
        if (updatedAbility == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update ability with id: " + id);
        }
        return ResponseEntity.ok(updatedAbility);
    }

    @PatchMapping("/abilities/patch/{id}")
    public ResponseEntity<Object> patchAbility(@PathVariable Long id,
                                               @RequestBody Ability ability) {
        Ability patchedAbility = abilityService.patchAbility(id, ability);
        if (patchedAbility == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to patch ability with id: " + id);
        }
        return ResponseEntity.ok(patchedAbility);
    }

    @DeleteMapping("/abilities/delete/{id}")
    public ResponseEntity<String> deleteAbility(@PathVariable Long id) {
        try {
            abilityService.deleteAbility(id);
            return ResponseEntity.ok("Deleted ability id - " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to delete ability with id: " + id);
        }
    }
}
