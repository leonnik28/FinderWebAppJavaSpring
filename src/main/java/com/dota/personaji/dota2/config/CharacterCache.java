package com.dota.personaji.dota2.config;

import com.dota.personaji.dota2.model.DotaCharacter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Component;

@Component
public class CharacterCache {

    private final ConcurrentMap<Long, DotaCharacter> cache = new ConcurrentHashMap<>();

    public void put(Long id, DotaCharacter character) {
        cache.put(id, character);
    }

    public DotaCharacter get(Long id) {
        return cache.get(id);
    }

    public boolean contains(Long id) {
        return cache.containsKey(id);
    }

    public void remove(Long id) {
        cache.remove(id);
    }

    public List<DotaCharacter> getAllCharacters() {
        return new ArrayList<>(cache.values());
    }

    public void putAllCharacters(List<DotaCharacter> characters) {
        characters.forEach(character -> cache.put(character.getId(), character));
    }
}
