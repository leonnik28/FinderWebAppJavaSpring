package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.dao.RoleRepository;
import com.dota.personaji.dota2.model.DotaCharacter;
import com.dota.personaji.dota2.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public List<DotaCharacter> getCharactersByRoleName(String roleName) {
        Role role = roleRepository.findByName(roleName);
        return role.getCharacters();
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, Role role) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole != null) {
            existingRole.setName(role.getName());
            roleRepository.save(existingRole);
        }
        return existingRole;
    }

    public Role patchRole(Long id, Role role) {
        Role existingRole = roleRepository.findById(id).orElse(null);
        if (existingRole != null) {
            if (role.getName() != null) {
                existingRole.setName(role.getName());
            }
            roleRepository.save(existingRole);
        }
        return existingRole;
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
