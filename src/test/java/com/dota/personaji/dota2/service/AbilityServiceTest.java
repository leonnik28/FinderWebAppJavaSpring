package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.model.Ability;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AbilityServiceTest {

    @InjectMocks
    private AbilityService abilityService;

    @Mock
    private AbilityRepository abilityRepository;

    @Test
    void testGetAllAbilities() {
        Ability ability = new Ability();
        when(abilityRepository.findAll()).thenReturn(List.of(ability));
        assertEquals(1, abilityService.getAllAbilities().size());
    }

    @Test
    void testGetAbilityById() {
        Ability ability = new Ability();
        when(abilityRepository.findById(anyLong())).thenReturn(Optional.of(ability));
        assertEquals(ability, abilityService.getAbilityById(1L));
    }

    @Test
    void testSaveAbility() {
        Ability ability = new Ability();
        when(abilityRepository.save(any(Ability.class))).thenReturn(ability);
        assertEquals(ability, abilityService.saveAbility(ability));
    }

    @Test
    void testUpdateAbility() {
        Ability ability = new Ability();
        Ability abilityDetails = new Ability();
        when(abilityRepository.findById(anyLong())).thenReturn(Optional.of(ability));
        when(abilityRepository.save(any(Ability.class))).thenReturn(ability);
        assertEquals(ability, abilityService.updateAbility(1L, abilityDetails));
    }

    @Test
    void testPatchAbility() {
        Ability ability = new Ability();
        Ability abilityDetails = new Ability();
        when(abilityRepository.findById(anyLong())).thenReturn(Optional.of(ability));
        when(abilityRepository.save(any(Ability.class))).thenReturn(ability);
        assertEquals(ability, abilityService.patchAbility(1L, abilityDetails));
    }

    @Test
    void testDeleteAbility() {
        Mockito.doNothing().when(abilityRepository).deleteById(anyLong());
        abilityService.deleteAbility(1L);
        verify(abilityRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetAbilityByIdNotFound() {
        when(abilityRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> abilityService.getAbilityById(1L));
    }

    @Test
    void testUpdateAbilityNotFound() {
        when(abilityRepository.findById(anyLong())).thenReturn(Optional.empty());
        Ability abilityDetails = new Ability();
        assertThrows(RuntimeException.class, () -> abilityService.updateAbility(1L, abilityDetails));
    }

    @Test
    void testPatchAbilityNotFound() {
        when(abilityRepository.findById(anyLong())).thenReturn(Optional.empty());
        Ability abilityDetails = new Ability();
        assertThrows(RuntimeException.class, () -> abilityService.patchAbility(1L, abilityDetails));
    }

    @Test
    void testPatchAbilityName() {
        Ability ability = new Ability();
        Ability abilityDetails = new Ability();
        abilityDetails.setName("New Name");
        when(abilityRepository.findById(any())).thenReturn(Optional.of(ability));
        when(abilityRepository.save(any())).thenReturn(ability);
        assertEquals("New Name", abilityService.patchAbility(1L, abilityDetails).getName());
    }

    @Test
    void testPatchAbilityDescription() {
        Ability ability = new Ability();
        Ability abilityDetails = new Ability();
        abilityDetails.setDescription("New Description");
        when(abilityRepository.findById(any())).thenReturn(Optional.of(ability));
        when(abilityRepository.save(any())).thenReturn(ability);
        assertEquals("New Description", abilityService.patchAbility(1L, abilityDetails).getDescription());
    }

}
