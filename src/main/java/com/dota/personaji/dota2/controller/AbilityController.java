package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.model.Ability;
import com.dota.personaji.dota2.service.AbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AbilityController {

    private final AbilityService abilityService;

    @Autowired
    public AbilityController(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @GetMapping("/abilities")
    public List<Ability> getAbilities(){
        return abilityService.getAllAbilities();
    }

    @GetMapping("/abilities/{id}")
    public Ability getAbility(@PathVariable Long id) {
        return abilityService.getAbilityById(id);
    }

    @PostMapping("/abilities/create")
    public Ability createAbility(@RequestBody Ability ability) {
        return abilityService.saveAbility(ability);
    }

    @PutMapping("/abilities/update/{id}")
    public Ability updateAbility(@PathVariable Long id, @RequestBody Ability ability) {
        return abilityService.updateAbility(id, ability);
    }

    @PatchMapping("/abilities/patch/{id}")
    public Ability patchAbility(@PathVariable Long id, @RequestBody Ability ability) {
        return abilityService.patchAbility(id, ability);
    }

    @DeleteMapping("/abilities/delete/{id}")
    public void deleteAbility(@PathVariable Long id) {
        abilityService.deleteAbility(id);
    }
}
