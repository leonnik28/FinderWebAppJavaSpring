package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.config.CharacterCache;
import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.dao.CharacterRepository;
import com.dota.personaji.dota2.model.Ability;
import com.dota.personaji.dota2.model.DotaCharacter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DotaCharacterServiceTest {

    @InjectMocks
    private DotaCharacterService dotaCharacterService;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private AbilityRepository abilityRepository;

    @Mock
    private CharacterCache cache;

    @Test
    public void testGetAllCharacters() {
        DotaCharacter character1 = new DotaCharacter();
        DotaCharacter character2 = new DotaCharacter();
        when(characterRepository.findAll()).thenReturn(Arrays.asList(character1, character2));
        assertEquals(2, dotaCharacterService.getAllCharacters().size());
    }

    @Test
    public void testGetCharacterById() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        assertEquals(character, dotaCharacterService.getCharacterById(1L));
    }

    @Test
    public void testGetCharacterByName() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findByName(any())).thenReturn(Arrays.asList(character));
        assertEquals(1, dotaCharacterService.getCharacterByName("name").size());
    }

    @Test
    public void testGetCharactersByPowerDesc() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findAllByOrderByPowerDesc()).thenReturn(Arrays.asList(character));
        assertEquals(1, dotaCharacterService.getCharactersByPowerDesc().size());
    }

    @Test
    public void testGetCharactersByAgilityDesc() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findAllByOrderByAgilityDesc()).thenReturn(Arrays.asList(character));
        assertEquals(1, dotaCharacterService.getCharactersByAgilityDesc().size());
    }

    @Test
    public void testGetCharactersByIntelligenceDesc() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findAllByOrderByIntelligenceDesc()).thenReturn(Arrays.asList(character));
        assertEquals(1, dotaCharacterService.getCharactersByIntelligenceDesc().size());
    }

    @Test
    public void testSaveCharacter() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.save(any(DotaCharacter.class))).thenReturn(character);
        assertEquals(character, dotaCharacterService.saveCharacter(character, Arrays.asList(1L)));
    }

    @Test
    public void testUpdateCharacter() {
        DotaCharacter character = new DotaCharacter();
        DotaCharacter characterDetails = new DotaCharacter();
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(abilityRepository.findAllById(any())).thenReturn(Arrays.asList(new Ability()));
        when(characterRepository.save(any())).thenReturn(character);
        assertEquals(character, dotaCharacterService.updateCharacter(1L, characterDetails, Arrays.asList(1L)));
    }

    @Test
    public void testPatchCharacter() {
        DotaCharacter character = new DotaCharacter();
        DotaCharacter characterDetails = new DotaCharacter();
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(abilityRepository.findAllById(any())).thenReturn(Arrays.asList(new Ability()));
        when(characterRepository.save(any())).thenReturn(character);
        assertEquals(character, dotaCharacterService.patchCharacter(1L, characterDetails, Arrays.asList(1L)));
    }


    @Test
    public void testAddAbilitiesToCharacter() {
        DotaCharacter character = new DotaCharacter();
        Ability ability = new Ability();
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));
        when(abilityRepository.findAllById(anyList())).thenReturn(Arrays.asList(ability));
        when(characterRepository.save(any(DotaCharacter.class))).thenReturn(character);
        assertEquals(character, dotaCharacterService.addAbilitiesToCharacter(1L, Arrays.asList(1L)));
    }

    @Test
    public void testRemoveAbilitiesFromCharacter() {
        DotaCharacter character = new DotaCharacter();
        Ability ability = new Ability();
        character.setAbilities(Arrays.asList(ability));
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));
        when(characterRepository.save(any(DotaCharacter.class))).thenReturn(character);
        assertEquals(character, dotaCharacterService.removeAbilitiesFromCharacter(1L, Arrays.asList(1L)));
    }


    @Test
    public void testGetStrongCharacters() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findStrongCharacters(anyInt())).thenReturn(Arrays.asList(character));
        assertEquals(1, dotaCharacterService.getStrongCharacters(100).size());
    }

    @Test
    public void testDeleteCharacter() {
        String expectedMessage = "Deleted character id - 1";
        assertEquals(expectedMessage, dotaCharacterService.deleteCharacter(1L));
    }

    @Test
    public void testCreateBulkCharacters() {
        DotaCharacter character1 = new DotaCharacter();
        DotaCharacter character2 = new DotaCharacter();
        List<DotaCharacter> characters = Arrays.asList(character1, character2);
        when(characterRepository.saveAll(anyList())).thenReturn(characters);
        assertEquals(2, dotaCharacterService.createBulkCharacters(characters).size());
    }

    @Test
    public void testDeleteBulkCharacters() {
        DotaCharacter character1 = new DotaCharacter();
        DotaCharacter character2 = new DotaCharacter();
        List<DotaCharacter> characters = Arrays.asList(character1, character2);
        doNothing().when(characterRepository).deleteAll(anyList());
        dotaCharacterService.deleteBulkCharacters(characters);
        verify(characterRepository, times(1)).deleteAll(characters);
    }

}