package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.config.CharacterCache;
import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.model.Ability;
import com.dota.personaji.dota2.model.DotaCharacter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DotaCharacterService {

    private final CharacterRepository characterRepository;
    private final AbilityRepository abilityRepository;
    private final CharacterCache cache;
    private final RequestCounterService requestCounterService;
    private static final String CHARACTER_NOT_FOUND_MESSAGE =
            "Character not found for this id :: ";

    @Autowired
    public DotaCharacterService(CharacterRepository characterRepository,
                                AbilityRepository abilityRepository,
                                CharacterCache cache, RequestCounterService requestCounterService) {
        this.characterRepository = characterRepository;
        this.abilityRepository = abilityRepository;
        this.cache = cache;
        this.requestCounterService = requestCounterService;
    }

    public List<DotaCharacter> getAllCharacters() {
        if (cache.getAllCharacters().isEmpty()) {
            List<DotaCharacter> characters = characterRepository.findAll();
            characters.forEach(character -> cache.put(character.getId(), character));
            return characters;
        } else {
            return cache.getAllCharacters();
        }
    }

    public DotaCharacter getCharacterById(Long id) {
        return Optional.ofNullable(cache.get(id))
                .orElseGet(() -> characterRepository.findById(id)
                        .map(character -> {
                            cache.put(id, character);
                            return character;
                        })
                        .orElse(null));
    }

    public List<DotaCharacter> getCharacterByName(String name) {
        return characterRepository.findByName(name);
    }

    public List<DotaCharacter> getCharactersByPowerDesc() {
        return characterRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(DotaCharacter::getPower).reversed())
                .toList();
    }

    public List<DotaCharacter> getCharactersByAgilityDesc() {
        return characterRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(DotaCharacter::getAgility).reversed())
                .toList();
    }

    public List<DotaCharacter> getCharactersByIntelligenceDesc() {
        return characterRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(DotaCharacter::getIntelligence).reversed())
                .toList();
    }

    public DotaCharacter getCharacterByAbility(Ability ability) {
        return characterRepository.findByAbilitiesContaining(ability);
    }

    public DotaCharacter saveCharacter(DotaCharacter dotaCharacter, List<Long> abilityIds) {
        dotaCharacter.setAbilities(abilityRepository.findAllById(abilityIds));
        DotaCharacter savedCharacter = characterRepository.save(dotaCharacter);
        cache.put(savedCharacter.getId(), savedCharacter);
        return savedCharacter;
    }

    public DotaCharacter updateCharacter(Long id, DotaCharacter characterDetails, List<Long> abilityIds) {
        DotaCharacter character = characterRepository.findById(id)
                .map(c -> {
                    c.setName(characterDetails.getName());
                    c.setPower(characterDetails.getPower());
                    c.setAgility(characterDetails.getAgility());
                    c.setIntelligence(characterDetails.getIntelligence());
                    c.setAttackType(characterDetails.getAttackType());
                    c.setAbilities(abilityRepository.findAllById(abilityIds));
                    return characterRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));
        cache.put(character.getId(), character);
        return character;
    }

    public DotaCharacter patchCharacter(Long id, DotaCharacter characterDetails, List<Long> abilityIds) {
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
                })
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));
    }

    public DotaCharacter addAbilitiesToCharacter(Long id, List<Long> abilityIds) {
        return characterRepository.findById(id)
                .map(character -> {
                    List<Ability> abilitiesToAdd = abilityRepository.findAllById(abilityIds);
                    List<Ability> existingAbilities = character.getAbilities();
                    if (existingAbilities == null) {
                        character.setAbilities(abilitiesToAdd);
                    } else {
                        for (Ability ability : abilitiesToAdd) {
                            if (!existingAbilities.contains(ability)) {
                                existingAbilities.add(ability);
                            }
                        }
                        character.setAbilities(existingAbilities);
                    }
                    return characterRepository.save(character);
                })
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));
    }


    public DotaCharacter removeAbilitiesFromCharacter(Long id, List<Long> abilityIds) {
        return characterRepository.findById(id)
                .map(character -> {
                    List<Ability> existingAbilities = character.getAbilities();
                    if (existingAbilities != null) {
                        existingAbilities.removeIf(ability -> abilityIds.contains(ability.getId()));
                        character.setAbilities(existingAbilities);
                    }
                    return characterRepository.save(character);
                })
                .orElseThrow(() -> new RuntimeException(CHARACTER_NOT_FOUND_MESSAGE + id));
    }

    public List<DotaCharacter> getStrongCharacters(int power) {
        requestCounterService.increment();
        return characterRepository.findStrongCharacters(power);
    }

    public List<DotaCharacter> findClosestCharactersByName(String name) {
        DotaCharacter character = characterRepository.findByName(name).stream().findFirst().orElse(null);

        if (character == null) {
            return Collections.emptyList();
        }

        Long characterId = character.getId();
        List<DotaCharacter> allCharacters = characterRepository.findAll();
        DotaCharacter previousCharacter = null;
        DotaCharacter nextCharacter = null;

        for (DotaCharacter currentCharacter : allCharacters) {
            Long currentId = currentCharacter.getId();

            if (currentId.equals(characterId)) {
                continue;
            }

            if (currentId < characterId && (previousCharacter == null || currentId > previousCharacter.getId())) {
                previousCharacter = currentCharacter;
            }

            if (currentId > characterId && (nextCharacter == null || currentId < nextCharacter.getId())) {
                nextCharacter = currentCharacter;
            }
        }

        return Stream.of(previousCharacter, nextCharacter)
                .filter(Objects::nonNull)
                .toList();

    }

    public String deleteCharacter(Long id) {
        characterRepository.deleteById(id);
        cache.remove(id);
        return "Deleted character id - " + id;
    }

    public List<DotaCharacter> createBulkCharacters(List<DotaCharacter> dotaCharacters) {
        List<DotaCharacter> savedCharacters = characterRepository.saveAll(dotaCharacters);
        savedCharacters.forEach(character -> cache.put(character.getId(), character));
        return savedCharacters;
    }

    public void deleteBulkCharacters(List<DotaCharacter> dotaCharacters) {
        characterRepository.deleteAll(dotaCharacters);
        dotaCharacters.forEach(character -> cache.remove(character.getId()));
    }
}
