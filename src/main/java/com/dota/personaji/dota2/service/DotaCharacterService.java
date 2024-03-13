package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.model.Ability;
import com.dota.personaji.dota2.config.CharacterCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

@Service
public class DotaCharacterService {

    private static final Logger logger = LoggerFactory.getLogger(DotaCharacterService.class);

    private final CharacterRepository characterRepository;
    private final AbilityRepository abilityRepository;
    private final CharacterCache cache;

    private static final String CHARACTER_NOT_FOUND_MESSAGE = "Character not found for this id :: ";

    @Autowired
    public DotaCharacterService(CharacterRepository characterRepository, AbilityRepository abilityRepository, CharacterCache cache) {
        this.characterRepository = characterRepository;
        this.abilityRepository = abilityRepository;
        this.cache = cache;
    }

    public List<DotaCharacter> getAllCharacters() {
        if (!cache.getAllCharacters().isEmpty()) {
            logger.info("Retrieved all characters from cache.");
            return cache.getAllCharacters();
        } else {
            logger.info("Retrieving all characters from database.");
            List<DotaCharacter> characters = characterRepository.findAll();
            cache.putAllCharacters(characters);
            return characters;
        }
    }

    public DotaCharacter getCharacterById(Long id) {
        if (cache.contains(id)) {
            logger.info("Retrieved character for id '{}' from cache.", id);
            return cache.get(id);
        } else {
            logger.info("Retrieving character for id '{}' from database.", id);
            DotaCharacter character = characterRepository.findById(id).orElse(null);
            if (character != null) {
                cache.put(id, character);
            }
            return character;
        }
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
        DotaCharacter savedCharacter = characterRepository.save(dotaCharacter);
        cache.put(savedCharacter.getId(), savedCharacter);
        return savedCharacter;
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

    public DotaCharacter addAbilitiesToCharacter(Long id, List<Long> abilityIds) {
        DotaCharacter character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));

        List<Ability> abilitiesToAdd = abilityRepository.findAllById(abilityIds);
        List<Ability> existingAbilities = character.getAbilities();
        if (existingAbilities == null) {
            character.setAbilities(abilitiesToAdd);
        } else {
            existingAbilities.addAll(abilitiesToAdd);
            character.setAbilities(existingAbilities);
        }

        return characterRepository.save(character);
    }

    public DotaCharacter removeAbilitiesFromCharacter(Long id, List<Long> abilityIds) {
        DotaCharacter character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));

        List<Ability> existingAbilities = character.getAbilities();
        if (existingAbilities != null) {
            existingAbilities.removeIf(ability -> abilityIds.contains(ability.getId()));
            character.setAbilities(existingAbilities);
        }

        return characterRepository.save(character);
    }

    public List<DotaCharacter> getStrongCharacters(int power) {
        return characterRepository.findStrongCharacters(power);
    }

    public String deleteCharacter(Long id) {
        characterRepository.deleteById(id);
        cache.remove(id);
        logger.info("Deleted character for id '{}' from database.", id);
        return "Deleted character id - " + id;
    }
}
