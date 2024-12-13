package com.example.Asterix.API.Services;


import com.example.Asterix.API.Model.AsterixCharacter;
import com.example.Asterix.API.Repositorie.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public double getAverageAgeByProfession(String profession) {
        List<AsterixCharacter> characters = characterRepository.findAgesByProfession(profession);
        return characters.stream()
                .mapToInt(AsterixCharacter::age)
                .average()
                .orElse(0); // Return 0 if no characters are found
    }

    public AsterixCharacter getCharacterById(String id) {
        return characterRepository.findById(id).orElseThrow(() -> new RuntimeException("Character with id " + id + " not found."));
    }

    public AsterixCharacter saveCharacter(AsterixCharacter character) {
        return characterRepository.save(character);
    }

    public AsterixCharacter updateCharacter(String id, AsterixCharacter updateCharacter) {
        AsterixCharacter existingCharacter = getCharacterById(id); // Fetch character or throw exception
        AsterixCharacter updatedCharacter = new AsterixCharacter(
                existingCharacter.id(),  // Preserve the ID
                updateCharacter.name(),
                updateCharacter.age(),
                updateCharacter.profession()
        );
        return characterRepository.save(updatedCharacter);
    }

    public void deleteupdateCharacter(String id) {
        characterRepository.deleteById(id);
    }
}
