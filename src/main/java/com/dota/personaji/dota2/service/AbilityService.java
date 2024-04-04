package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.model.Ability;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbilityService {

    private final AbilityRepository abilityRepository;

    private static final String ABILITY_NOT_FOUND_MESSAGE =
            "Ability not found for this id :: ";

    @Autowired
    public AbilityService(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
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
        return abilityRepository.findById(id)
                .map(ability -> {
                    ability.setName(abilityDetails.getName());
                    ability.setDescription(abilityDetails.getDescription());
                    return abilityRepository.save(ability);
                }).orElseThrow(() -> new RuntimeException(ABILITY_NOT_FOUND_MESSAGE + id));
    }

    public Ability patchAbility(Long id, Ability abilityDetails) {
        return abilityRepository.findById(id)
                .map(ability -> {
                    if (abilityDetails.getName() != null) {
                        ability.setName(abilityDetails.getName());
                    }
                    if (abilityDetails.getDescription() != null) {
                        ability.setDescription(abilityDetails.getDescription());
                    }
                    return abilityRepository.save(ability);
                }).orElseThrow(() -> new RuntimeException(ABILITY_NOT_FOUND_MESSAGE + id));
    }

    public void deleteAbility(Long id) {
        abilityRepository.deleteById(id);
    }
}
