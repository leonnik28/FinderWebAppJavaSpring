package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.model.DotaCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DotaCharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public DotaCharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<DotaCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }

    public DotaCharacter getCharacterById(Long id) {
        return characterRepository.findById(id).orElse(null);
    }

    public void saveCharacter(DotaCharacter dotaCharacter) {
        characterRepository.save(dotaCharacter);
    }

    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }

    public List<DotaCharacter> getCharactersByPowerDesc() {
        return characterRepository.findAllByOrderByPowerDesc();
    }

    public List<DotaCharacter> getCharactersByDexterityDesc() {
        return characterRepository.findAllByOrderByDexterityDesc();
    }

    public List<DotaCharacter> getCharactersByIntelligenceDesc() {
        return characterRepository.findAllByOrderByIntelligenceDesc();
    }

    public List<DotaCharacter> getCharacterByName(String name) {
        return characterRepository.findByName(name);
    }
}