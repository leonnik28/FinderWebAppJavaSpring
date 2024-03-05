package com.dota.personaji.dota2.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<DotaCharacter> characters;

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

    public List<DotaCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(List<DotaCharacter> characters) {
        this.characters = characters;
    }

    public void addCharacter(DotaCharacter character) {
        if (characters == null) {
            characters = new ArrayList<>();
        }
        characters.add(character);
        character.getRoles().add(this);
    }
}
