package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.model.Ability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DotaCharacterService {

    private final CharacterRepository characterRepository;
    private final AbilityRepository abilityRepository;

    private static final String CHARACTER_NOT_FOUND_MESSAGE = "Character not found for this id :: ";

    @Autowired
    public DotaCharacterService(CharacterRepository characterRepository, AbilityRepository abilityRepository) {
        this.characterRepository = characterRepository;
        this.abilityRepository = abilityRepository;
    }

    public List<DotaCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }

    public DotaCharacter getCharacterById(Long id) {
        return characterRepository.findById(id).orElse(null);
    }

    public List<DotaCharacter> getCharacterByName(String name) {
        return characterRepository.findByName(name);
    }


    public List<DotaCharacter> getCharactersByPowerDesc() {
        return characterRepository.findAllByOrderByPowerDesc();
    }

    public List<DotaCharacter> getCharactersByAgilityDesc() {
        return characterRepository.findAllByOrderByAgilityDesc();
    }

    public List<DotaCharacter> getCharactersByIntelligenceDesc() {
        return characterRepository.findAllByOrderByIntelligenceDesc();
    }

    public DotaCharacter saveCharacter(DotaCharacter dotaCharacter, List<Long> abilityIds) {
        List<Ability> abilities = abilityRepository.findAllById(abilityIds);
        dotaCharacter.setAbilities(abilities);

        return characterRepository.save(dotaCharacter);
    }

    public DotaCharacter updateCharacter(Long id, DotaCharacter characterDetails, List<Long> abilityIds) {
        DotaCharacter character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));

        character.setName(characterDetails.getName());
        character.setPower(characterDetails.getPower());
        character.setAgility(characterDetails.getAgility());
        character.setIntelligence(characterDetails.getIntelligence());
        character.setAttackType(characterDetails.getAttackType());

        List<Ability> abilities = abilityRepository.findAllById(abilityIds);
        character.setAbilities(abilities);

        return characterRepository.save(character);
    }

    public DotaCharacter patchCharacter(Long id, DotaCharacter characterDetails, List<Long> abilityIds) {
        DotaCharacter character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));

        if (characterDetails.getName() != null) {
            character.setName(characterDetails.getName());
        }
        if (characterDetails.getPower() != 0) {
            character.setPower(characterDetails.getPower());
        }
        if (characterDetails.getAgility() != 0) {
            character.setAgility(characterDetails.getAgility());
        }
        if (characterDetails.getIntelligence() != 0) {
            character.setIntelligence(characterDetails.getIntelligence());
        }
        if (characterDetails.getAttackType() != null) {
            character.setAttackType(characterDetails.getAttackType());
        }
        if (abilityIds != null) {
            List<Ability> abilities = abilityRepository.findAllById(abilityIds);
            character.setAbilities(abilities);
        }

        return characterRepository.save(character);
    }


    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }
}
