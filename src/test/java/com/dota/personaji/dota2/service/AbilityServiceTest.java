package com.dota.personaji.dota2.service;

import com.dota.personaji.dota2.config.CharacterCache;
import com.dota.personaji.dota2.dao.AbilityRepository;
import com.dota.personaji.dota2.model.Ability;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AbilityServiceTest {

    @InjectMocks
    private AbilityService abilityService;

    @Mock
    private AbilityRepository abilityRepository;

    @Mock
    private CharacterCache cache;

    @Test
    public void testGetAllAbilities() {
        Ability ability = new Ability();
        when(abilityRepository.findAll()).thenReturn(Arrays.asList(ability));
        assertEquals(1, abilityService.getAllAbilities().size());
    }

    @Test
    public void testGetAbilityById() {
        Ability ability = new Ability();
        when(abilityRepository.findById(anyLong())).thenReturn(Optional.of(ability));
        assertEquals(ability, abilityService.getAbilityById(1L));
    }

    @Test
    public void testSaveAbility() {
        Ability ability = new Ability();
        when(abilityRepository.save(any(Ability.class))).thenReturn(ability);
        assertEquals(ability, abilityService.saveAbility(ability));
    }

    @Test
    public void testUpdateAbility() {
        Ability ability = new Ability();
        Ability abilityDetails = new Ability();
        when(abilityRepository.findById(anyLong())).thenReturn(Optional.of(ability));
        when(abilityRepository.save(any(Ability.class))).thenReturn(ability);
        assertEquals(ability, abilityService.updateAbility(1L, abilityDetails));
    }

    @Test
    public void testPatchAbility() {
        Ability ability = new Ability();
        Ability abilityDetails = new Ability();
        when(abilityRepository.findById(anyLong())).thenReturn(Optional.of(ability));
        when(abilityRepository.save(any(Ability.class))).thenReturn(ability);
        assertEquals(ability, abilityService.patchAbility(1L, abilityDetails));
    }

    @Test
    public void testDeleteAbility() {
        Mockito.doNothing().when(abilityRepository).deleteById(anyLong());
        abilityService.deleteAbility(1L);
        verify(abilityRepository, times(1)).deleteById(anyLong());
    }
}
