package com.dota.personaji.dota2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private String picUrl;
    private String urlVideo;

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

    public String getPicUrl(){ return picUrl; }

    public void setPicUrl(String picUrl) { this.picUrl = picUrl; }

    public String getUrlVideo(){ return urlVideo;}

    public void setUrlVideo(String urlVideo) { this.urlVideo = urlVideo;}
}
