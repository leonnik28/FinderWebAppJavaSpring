package com.dota.personaji.dota2.dao;

import com.dota.personaji.dota2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByOrderByNameDesc();
    Role findByName(String name);
}
