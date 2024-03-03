package com.dota.personaji.dota2.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name = "abilities")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "character_id")
    @JsonManagedReference
    private DotaCharacter character;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DotaCharacter getCharacter() { return character; }

    public void setCharacter(DotaCharacter dotaCharacter) { this.character = dotaCharacter; }
}
