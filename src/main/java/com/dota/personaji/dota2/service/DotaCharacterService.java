package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.config.CharacterCache;
import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.model.Ability;
import com.dota.personaji.dota2.model.DotaCharacter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DotaCharacterService {

    private final CharacterRepository characterRepository;
    private final AbilityRepository abilityRepository;
    private final CharacterCache cache;

    private static final String CHARACTER_NOT_FOUND_MESSAGE =
            "Character not found for this id :: ";


    @Autowired
    public DotaCharacterService(CharacterRepository characterRepository,
                                AbilityRepository abilityRepository,
                                CharacterCache cache) {
        this.characterRepository = characterRepository;
        this.abilityRepository = abilityRepository;
        this.cache = cache;
    }

    public List<DotaCharacter> getAllCharacters() {
        if (cache.getAllCharacters().isEmpty()) {
            List<DotaCharacter> characters = characterRepository.findAll();
            characters.forEach(character -> cache.put(character.getId(),
                    character));
            return characters;
        } else {
            return cache.getAllCharacters();
        }
    }


    public DotaCharacter getCharacterById(Long id) {
        return cache.contains(id) ? cache.get(id) : characterRepository.findById(id)
                .map(character -> {
                    cache.put(id, character);
                    return character;
                }).orElse(null);
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
        dotaCharacter.setAbilities(abilityRepository.findAllById(abilityIds));
        DotaCharacter savedCharacter = characterRepository.save(dotaCharacter);
        cache.put(savedCharacter.getId(), savedCharacter);
        return savedCharacter;
    }

    public DotaCharacter updateCharacter(Long id,
                                         DotaCharacter characterDetails,
                                         List<Long> abilityIds) {
        return characterRepository.findById(id)
                .map(character -> {
                    character.setName(characterDetails.getName());
                    character.setPower(characterDetails.getPower());
                    character.setAgility(characterDetails.getAgility());
                    character.setIntelligence(characterDetails.getIntelligence());
                    character.setAttackType(characterDetails.getAttackType());
                    character.setAbilities(abilityRepository.findAllById(abilityIds));
                    return characterRepository.save(character);
                }).orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));
    }

    public DotaCharacter patchCharacter(Long id,
                                        DotaCharacter characterDetails,
                                        List<Long> abilityIds) {
        return characterRepository.findById(id)
                .map(character -> {
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
                        character.setAbilities(abilityRepository.findAllById(abilityIds));
                    }
                    return characterRepository.save(character);
                }).orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));
    }

    public DotaCharacter addAbilitiesToCharacter(Long id, List<Long> abilityIds) {
        DotaCharacter character = characterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE
                        + id));

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
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE
                        + id));

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
        return "Deleted character id - " + id;
    }

    public List<DotaCharacter> createBulkCharacters(List<DotaCharacter> dotaCharacters) {
        return characterRepository.saveAll(dotaCharacters);
    }

    public void deleteBulkCharacters(List<DotaCharacter> dotaCharacters) {
        characterRepository.deleteAll(dotaCharacters);
    }

}
