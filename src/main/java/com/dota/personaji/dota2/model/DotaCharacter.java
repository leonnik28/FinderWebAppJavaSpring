package com.dota.personaji.dota2.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "dotacharacters")
public class DotaCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int power;
    private int agility;
    private int intelligence;
    private String attacktype;
    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Ability> abilities;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "character_role",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int dexterity) {
        this.agility = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public String getAttacktype() {
        return attacktype;
    }

    public void setAttacktype(String appointment) {
        this.attacktype = appointment;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public void addAbility(Ability ability) {
        if (abilities == null) {
            abilities = new ArrayList<>();
        }
        abilities.add(ability);
        ability.setCharacter(this);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
        role.getCharacters().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getCharacters().remove(this);
    }
}
