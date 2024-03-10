package com.dota.personaji.dota2.dao;

import com.dota.personaji.dota2.model.DotaCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<DotaCharacter, Long> {
    List<DotaCharacter> findAllByOrderByPowerDesc();
    List<DotaCharacter> findAllByOrderByAgilityDesc();
    List<DotaCharacter> findAllByOrderByIntelligenceDesc();
    List<DotaCharacter> findByName(String name);
}