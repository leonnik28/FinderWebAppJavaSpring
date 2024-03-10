package com.dota.personaji.dota2.dto;

import java.util.List;

public class DotaCharacterDTO {
    private String name;
    private int power;
    private int agility;
    private int intelligence;
    private String attackType;
    private List<Long> abilityIds;

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

    public void setAgility(int agility) {
        this.agility = agility;
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

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public List<Long> getAbilityIds() {
        return abilityIds;
    }

    public void setAbilityIds(List<Long> abilityIds) {
        this.abilityIds = abilityIds;
    }
}

