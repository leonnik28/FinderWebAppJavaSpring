package com.dota.personaji.dota2.dao;

import com.dota.personaji.dota2.model.DotaCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<DotaCharacter, Long> {
    List<DotaCharacter> findAllByOrderByPowerDesc();
    List<DotaCharacter> findAllByOrderByAgilityDesc();
    List<DotaCharacter> findAllByOrderByIntelligenceDesc();
    List<DotaCharacter> findByName(String name);

    @Query("SELECT c FROM DotaCharacter c WHERE c.power > :power")
    List<DotaCharacter> findStrongCharacters(@Param("power") int power);


}