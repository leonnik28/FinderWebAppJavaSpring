package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.model.DotaCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DotaCharacterService {

    private final CharacterRepository CharacterRepository;

    @Autowired
    public DotaCharacterService(CharacterRepository CharacterRepository) {
        this.CharacterRepository = CharacterRepository;
    }

    public List<DotaCharacter> getAllCharacters() {
        return CharacterRepository.findAll();
    }

    public DotaCharacter getCharacterById(Long id) {
        return CharacterRepository.findById(id).orElse(null);
    }

    public void saveCharacter(DotaCharacter dotaCharacter) {
        CharacterRepository.save(dotaCharacter);
    }

    public void deleteCharacter(Long id) {
        CharacterRepository.deleteById(id);
    }

    public List<DotaCharacter> getCharactersByPowerDesc() {
        return CharacterRepository.findAllByOrderByPowerDesc();
    }

    public List<DotaCharacter> getCharactersByDexterityDesc() {
        return CharacterRepository.findAllByOrderByDexterityDesc();
    }

    public List<DotaCharacter> getCharactersByIntelligenceDesc() {
        return CharacterRepository.findAllByOrderByIntelligenceDesc();
    }

    public List<DotaCharacter> getCharacterByName(String name) {
        return CharacterRepository.findByName(name);
    }
}