package com.example.Asterix.API.Controller;


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
}
