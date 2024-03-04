package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.model.Ability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbilityService {

    private final AbilityRepository abilityRepository;
    private final CharacterRepository characterRepository;

    private static final String ABILITY_NOT_FOUND_MESSAGE = "Ability not found for this id :: ";

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
                .orElseThrow(() -> new RuntimeException(ABILITY_NOT_FOUND_MESSAGE + id));

        ability.setName(abilityDetails.getName());
        ability.setDescription(abilityDetails.getDescription());

        return abilityRepository.save(ability);
    }

    public Ability patchAbility(Long id, Ability abilityDetails) {
        Ability ability = abilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ABILITY_NOT_FOUND_MESSAGE + id));

        if (abilityDetails.getName() != null) {
            ability.setName(abilityDetails.getName());
        }
        if (abilityDetails.getDescription() != null) {
            ability.setDescription(abilityDetails.getDescription());
        }

        return abilityRepository.save(ability);
    }

    public void deleteAbility(Long id) {
        abilityRepository.deleteById(id);
    }
}
