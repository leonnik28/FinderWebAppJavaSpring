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

    public List<Ability> getAbilitiesByCharacterId(Long id) {
        return abilityRepository.findByCharacterId(id);
    }

    public List<Ability> getAbilitiesByCharacterName(String name) {
        return abilityRepository.findByCharacterName(name);
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

    public void saveCharacter(DotaCharacter dotaCharacter) {
        characterRepository.save(dotaCharacter);
    }

    public DotaCharacter addAbilityToCharacter(Long characterId, Long abilityId) {
        DotaCharacter character = characterRepository.findById(characterId)
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + characterId));
        Ability ability = abilityRepository.findById(abilityId)
                .orElseThrow(() -> new RuntimeException("Ability not found for this id :: " + abilityId));

        character.addAbility(ability);
        return characterRepository.save(character);
    }

    public DotaCharacter updateCharacter(Long id, DotaCharacter characterDetails) {
        DotaCharacter character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));

        character.setName(characterDetails.getName());
        character.setPower(characterDetails.getPower());
        character.setAgility(characterDetails.getAgility());
        character.setIntelligence(characterDetails.getIntelligence());
        character.setAttacktype(characterDetails.getAttacktype());

        return characterRepository.save(character);
    }

    public DotaCharacter patchCharacter(Long id, DotaCharacter characterDetails) {
        DotaCharacter character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));

        if (characterDetails.getName() != null) {
            character.setName(characterDetails.getName());
        }
        if (characterDetails.getAttacktype() != null) {
            character.setAttacktype(characterDetails.getAttacktype());
        }

        return characterRepository.save(character);
    }

    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }
}
