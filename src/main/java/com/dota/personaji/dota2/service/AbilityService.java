package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.model.Ability;
import com.dota.personaji.dota2.model.DotaCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbilityService {

    private final AbilityRepository abilityRepository;
    private final CharacterRepository characterRepository;

    @Autowired
    public AbilityService(AbilityRepository abilityRepository, CharacterRepository characterRepository) {
        this.abilityRepository = abilityRepository;
        this.characterRepository = characterRepository;
    }

    public List<Ability> getAllAbilities() {
        return abilityRepository.findAll();
    }

    public Ability getAbilityById(Long id) {
        return abilityRepository.findById(id).orElse(null);
    }

    public Ability saveAbility(Ability ability) {
        return abilityRepository.save(ability);
    }

    public Ability updateAbility(Long id, Ability abilityDetails) {
        Ability ability = abilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ability not found for this id :: " + id));

        ability.setName(abilityDetails.getName());
        ability.setDescription(abilityDetails.getDescription());

        final Ability updatedAbility = abilityRepository.save(ability);
        return updatedAbility;
    }

    public Ability patchAbility(Long id, Ability abilityDetails) {
        Ability ability = abilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ability not found for this id :: " + id));

        if (abilityDetails.getName() != null) {
            ability.setName(abilityDetails.getName());
        }
        if (abilityDetails.getDescription() != null) {
            ability.setDescription(abilityDetails.getDescription());
        }

        final Ability patchedAbility = abilityRepository.save(ability);
        return patchedAbility;
    }

    public void deleteAbility(Long id) {
        abilityRepository.deleteById(id);
    }
}
