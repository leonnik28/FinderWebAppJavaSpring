package com.dota.personaji.dota2.dao;

import com.dota.personaji.dota2.model.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbilityRepository extends JpaRepository<Ability, Long> {
    List<Ability> findAllByOrderByNameDesc();
    List<Ability> findByCharacterId(Long characterId);
    List<Ability> findByCharacterName(String name);
}