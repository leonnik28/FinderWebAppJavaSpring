package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.model.Role;
import com.dota.personaji.dota2.service.RoleService;
import com.dota.personaji.dota2.model.DotaCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/roles/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @GetMapping("/roles/name/{roleName}/characters")
    public List<DotaCharacter> getCharactersByRoleName(@PathVariable String roleName) {
        return roleService.getCharactersByRoleName(roleName);
    }

    @PostMapping("/roles/create")
    public Role saveRole(@RequestBody Role role) {
        roleService.saveRole(role);
        return role;
    }

    @PutMapping("/roles/update/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    @PatchMapping("/roles/patch/{id}")
    public Role patchRole(@PathVariable Long id, @RequestBody Role role) {
        return roleService.patchRole(id, role);
    }

    @DeleteMapping("/roles/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return "Deleted role id - " + id;
    }
}
