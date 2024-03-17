package com.dota.personaji.dota2.model;

import jakarta.persistence.*;
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
    private String attackType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
    private List<Ability> abilities;

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

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String appointment) {
        this.attackType = appointment;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

}
