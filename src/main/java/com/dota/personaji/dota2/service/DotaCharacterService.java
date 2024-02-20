package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.ICharacterRepository;
import com.dota.personaji.dota2.model.DotaCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DotaCharacterService {

    private final ICharacterRepository ICharacterRepository;

    @Autowired
    public DotaCharacterService(ICharacterRepository ICharacterRepository) {
        this.ICharacterRepository = ICharacterRepository;
    }

    public List<DotaCharacter> getAllCharacters() {
        return ICharacterRepository.findAll();
    }

    public DotaCharacter getCharacterById(Long id) {
        return ICharacterRepository.findById(id).orElse(null);
    }

    public void saveCharacter(DotaCharacter dotaCharacter) {
        ICharacterRepository.save(dotaCharacter);
    }

    public void deleteCharacter(Long id) {
        ICharacterRepository.deleteById(id);
    }

    public List<DotaCharacter> getCharactersByPowerDesc() {
        return ICharacterRepository.findAllByOrderByPowerDesc();
    }

    public List<DotaCharacter> getCharactersByDexterityDesc() {
        return ICharacterRepository.findAllByOrderByDexterityDesc();
    }

    public List<DotaCharacter> getCharactersByIntelligenceDesc() {
        return ICharacterRepository.findAllByOrderByIntelligenceDesc();
    }

    public List<DotaCharacter> getCharacterByName(String name) {
        return ICharacterRepository.findByName(name);
    }
}