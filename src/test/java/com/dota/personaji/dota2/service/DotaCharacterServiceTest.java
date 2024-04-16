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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DotaCharacterServiceTest {

    @InjectMocks
    private DotaCharacterService dotaCharacterService;

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private AbilityRepository abilityRepository;

    @Mock
    private CharacterCache characterCache;

    @Test
    void testGetAllCharacters() {
        DotaCharacter character1 = new DotaCharacter();
        DotaCharacter character2 = new DotaCharacter();
        when(characterRepository.findAll()).thenReturn(Arrays.asList(character1, character2));
        assertEquals(2, dotaCharacterService.getAllCharacters().size());
    }

    @Test
    void testGetAllCharactersWhenDatabaseIsEmpty() {
        DotaCharacter character1 = new DotaCharacter();
        DotaCharacter character2 = new DotaCharacter();
        when(characterRepository.findAll()).thenReturn(Collections.emptyList());
        when(characterCache.getAllCharacters()).thenReturn(Arrays.asList(character1, character2));
        assertEquals(2, dotaCharacterService.getAllCharacters().size());
    }

    @Test
    void testGetCharacterByIdFromCache() {
        DotaCharacter character = new DotaCharacter();
        when(characterCache.contains(anyLong())).thenReturn(true);
        when(characterCache.get(anyLong())).thenReturn(character);
        assertEquals(character, dotaCharacterService.getCharacterById(1L));
    }

    @Test
    void testGetCharacterByIdFromRepository() {
        DotaCharacter character = new DotaCharacter();
        when(characterCache.contains(anyLong())).thenReturn(false);
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));
        assertEquals(character, dotaCharacterService.getCharacterById(1L));
    }

    @Test
    void testGetCharacterById() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        assertEquals(character, dotaCharacterService.getCharacterById(1L));
    }

    @Test
    void testGetCharacterByName() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findByName(any())).thenReturn(List.of(character));
        assertEquals(1, dotaCharacterService.getCharacterByName("name").size());
    }

    @Test
    void testGetCharactersByPowerDesc() {
        DotaCharacter character = new DotaCharacter();
        character.setPower(10);
        when(characterRepository.findAll()).thenReturn(List.of(character));
        assertEquals(1, dotaCharacterService.getCharactersByPowerDesc().size());
        assertEquals(10, dotaCharacterService.getCharactersByPowerDesc().get(0).getPower());
    }

    @Test
    void testGetCharactersByAgilityDesc() {
        DotaCharacter character = new DotaCharacter();
        character.setAgility(10);
        when(characterRepository.findAll()).thenReturn(List.of(character));
        assertEquals(1, dotaCharacterService.getCharactersByAgilityDesc().size());
        assertEquals(10, dotaCharacterService.getCharactersByAgilityDesc().get(0).getAgility());
    }

    @Test
    void testGetCharactersByIntelligenceDesc() {
        DotaCharacter character = new DotaCharacter();
        character.setIntelligence(10);
        when(characterRepository.findAll()).thenReturn(List.of(character));
        assertEquals(1, dotaCharacterService.getCharactersByIntelligenceDesc().size());
        assertEquals(10, dotaCharacterService.getCharactersByIntelligenceDesc().get(0).getIntelligence());
    }

    @Test
    void testSaveCharacter() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.save(any(DotaCharacter.class))).thenReturn(character);
        assertEquals(character, dotaCharacterService.saveCharacter(character, List.of(1L)));
    }

    @Test
    void testUpdateCharacter() {
        DotaCharacter character = new DotaCharacter();
        DotaCharacter characterDetails = new DotaCharacter();
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(abilityRepository.findAllById(any())).thenReturn(List.of(new Ability()));
        when(characterRepository.save(any())).thenReturn(character);
        assertEquals(character, dotaCharacterService.updateCharacter(1L, characterDetails, List.of(1L)));
    }

    @Test
    void testPatchCharacter() {
        DotaCharacter character = new DotaCharacter();
        DotaCharacter characterDetails = new DotaCharacter();
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(abilityRepository.findAllById(any())).thenReturn(List.of(new Ability()));
        when(characterRepository.save(any())).thenReturn(character);
        assertEquals(character, dotaCharacterService.patchCharacter(1L, characterDetails, List.of(1L)));
    }

    @Test
    void testPatchCharacterName() {
        DotaCharacter character = new DotaCharacter();
        DotaCharacter characterDetails = new DotaCharacter();
        characterDetails.setName("New Name");
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(characterRepository.save(any())).thenReturn(character);
        assertEquals("New Name", dotaCharacterService.patchCharacter(1L, characterDetails, null).getName());
    }

    @Test
    void testPatchCharacterPower() {
        DotaCharacter character = new DotaCharacter();
        DotaCharacter characterDetails = new DotaCharacter();
        characterDetails.setPower(100);
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(characterRepository.save(any())).thenReturn(character);
        assertEquals(100, dotaCharacterService.patchCharacter(1L, characterDetails, null).getPower());
    }

    @Test
    void testPatchCharacterAgility() {
        DotaCharacter character = new DotaCharacter();
        DotaCharacter characterDetails = new DotaCharacter();
        characterDetails.setAgility(100);
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(characterRepository.save(any())).thenReturn(character);
        assertEquals(100, dotaCharacterService.patchCharacter(1L, characterDetails, null).getAgility());
    }

    @Test
    void testPatchCharacterIntelligence() {
        DotaCharacter character = new DotaCharacter();
        DotaCharacter characterDetails = new DotaCharacter();
        characterDetails.setIntelligence(100);
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(characterRepository.save(any())).thenReturn(character);
        assertEquals(100, dotaCharacterService.patchCharacter(1L, characterDetails, null).getIntelligence());
    }

    @Test
    void testPatchCharacterAttackType() {
        DotaCharacter character = new DotaCharacter();
        DotaCharacter characterDetails = new DotaCharacter();
        characterDetails.setAttackType("New Attack Type");
        when(characterRepository.findById(any())).thenReturn(Optional.of(character));
        when(characterRepository.save(any())).thenReturn(character);
        assertEquals("New Attack Type", dotaCharacterService.patchCharacter(1L, characterDetails, null).getAttackType());
    }

    @Test
    void testAddAbilitiesToCharacter() {
        DotaCharacter character = new DotaCharacter();
        Ability ability = new Ability();
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));
        when(abilityRepository.findAllById(anyList())).thenReturn(List.of(ability));
        when(characterRepository.save(any(DotaCharacter.class))).thenReturn(character);
        assertEquals(character, dotaCharacterService.addAbilitiesToCharacter(1L, List.of(1L)));
    }

    @Test
    void testAddAbilitiesToCharacterWhenAbilitiesExist() {
        DotaCharacter character = new DotaCharacter();
        character.setAbilities(new ArrayList<>(List.of(new Ability())));

        Ability newAbility = new Ability();
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));
        when(abilityRepository.findAllById(anyList())).thenReturn(List.of(newAbility));
        when(characterRepository.save(any(DotaCharacter.class))).thenReturn(character);

        DotaCharacter result = dotaCharacterService.addAbilitiesToCharacter(1L, List.of(1L));

        assertEquals(character, result);
        assertEquals(2, result.getAbilities().size());
    }

    @Test
    void testAddAbilitiesToCharacterWithAbilitiesDuplicate() {
        DotaCharacter character = new DotaCharacter();
        Ability ability = new Ability();

        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));
        when(abilityRepository.findAllById(anyList())).thenReturn(List.of(ability));
        when(characterRepository.save(any(DotaCharacter.class))).thenReturn(character);

        dotaCharacterService.addAbilitiesToCharacter(1L, List.of(1L));

        assertEquals(1, character.getAbilities().size());

        dotaCharacterService.addAbilitiesToCharacter(1L, List.of(1L));

        assertEquals(1, character.getAbilities().size());
    }

    @Test
    void testRemoveAbilitiesFromCharacter() {
        DotaCharacter character = new DotaCharacter();
        Ability ability = new Ability();
        ability.setId(1L);
        character.setAbilities(new ArrayList<>(List.of(ability)));

        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));
        when(characterRepository.save(any(DotaCharacter.class))).thenReturn(character);

        DotaCharacter result = dotaCharacterService.removeAbilitiesFromCharacter(1L, List.of(1L));

        assertEquals(character, result);
        assertTrue(result.getAbilities().isEmpty());
    }

    @Test
    void testRemoveAbilitiesFromCharacterWhenNoAbilitiesExist() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findById(anyLong())).thenReturn(Optional.of(character));
        when(characterRepository.save(any(DotaCharacter.class))).thenReturn(character);
        assertEquals(character, dotaCharacterService.removeAbilitiesFromCharacter(1L, List.of(1L)));
    }


    @Test
    void testGetStrongCharacters() {
        DotaCharacter character = new DotaCharacter();
        when(characterRepository.findStrongCharacters(anyInt())).thenReturn(List.of(character));
        assertEquals(1, dotaCharacterService.getStrongCharacters(100).size());
    }

    @Test
    void testDeleteCharacter() {
        String expectedMessage = "Deleted character id - 1";
        assertEquals(expectedMessage, dotaCharacterService.deleteCharacter(1L));
    }

    @Test
    void testCreateBulkCharacters() {
        DotaCharacter character1 = new DotaCharacter();
        DotaCharacter character2 = new DotaCharacter();
        List<DotaCharacter> characters = Arrays.asList(character1, character2);
        when(characterRepository.saveAll(anyList())).thenReturn(characters);
        assertEquals(2, dotaCharacterService.createBulkCharacters(characters).size());
    }

    @Test
    void testDeleteBulkCharacters() {
        DotaCharacter character1 = new DotaCharacter();
        DotaCharacter character2 = new DotaCharacter();
        List<DotaCharacter> characters = Arrays.asList(character1, character2);
        doNothing().when(characterRepository).deleteAll(anyList());
        dotaCharacterService.deleteBulkCharacters(characters);
        verify(characterRepository, times(1)).deleteAll(characters);
    }

}