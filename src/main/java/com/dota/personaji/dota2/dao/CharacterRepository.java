package com.dota.personaji.dota2.dao;

import com.dota.personaji.dota2.model.Ability;
import com.dota.personaji.dota2.model.DotaCharacter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<DotaCharacter, Long> {
    List<DotaCharacter> findByName(String name);

    DotaCharacter findByAbilitiesContaining(Ability ability);

    @Query(value = "SELECT * FROM dotacharacters c WHERE c.power > :power", nativeQuery = true)
    List<DotaCharacter> findStrongCharacters(@Param("power") int power);
}