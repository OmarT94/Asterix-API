package com.example.Asterix.API.Services;


import com.example.Asterix.API.Dto.CharacterDTO;
import com.example.Asterix.API.Model.AsterixCharacter;
import com.example.Asterix.API.Repositorie.CharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final IdService idService;

    public CharacterService(CharacterRepository characterRepository, IdService idService) {
        this.characterRepository = characterRepository;
        this.idService = idService;
    }


    public double getAverageAgeByProfession(String profession) {
        List<AsterixCharacter> characters = characterRepository.findAgesByProfession(profession);
        return characters.stream()
                .mapToInt(AsterixCharacter::age)
                .average()
                .orElse(0); // Return 0 if no characters are found
    }

    public CharacterDTO getCharacterById(String id) {
       AsterixCharacter character = characterRepository.findById(id).orElseThrow();
       CharacterDTO characterDTO = new CharacterDTO(
               character.id(),
               character.name(),
               character.age(),
               character.profession());
       return characterDTO;
    }

    public AsterixCharacter saveCharacter(CharacterDTO characterDTO) {
        String randomId = idService.generateId();
        AsterixCharacter newCharacter = new AsterixCharacter(
                randomId,
                characterDTO.name(),
                characterDTO.age(),
                characterDTO.profession()
        );



        return characterRepository.save(newCharacter);
    }

    public AsterixCharacter updateCharacter(AsterixCharacter updateCharacter,String id ) {
//        CharacterDTO existingCharacter = getCharacterById(id); // Fetch character or throw exception
//        AsterixCharacter updatedCharacter = new AsterixCharacter(
//                existingCharacter.id(),  // Preserve the ID
//                updateCharacter.name(),
//                updateCharacter.age(),
//                updateCharacter.profession()
//        );
//        return characterRepository.save(updatedCharacter);
        if(characterRepository.existsById(id)) {
            characterRepository.save(updateCharacter);
            return characterRepository.findById(id).orElseThrow();
        }else {
            throw new RuntimeException("Character not found");
        }
    }

    public void deleteCharacter(String id) {
        if(characterRepository.existsById(id)) {
            characterRepository.deleteById(id);
        }else{
            throw new RuntimeException("Character not found");
        }
    }

    public List<CharacterDTO> getAllCharacter() {
        return characterRepository.findAll().stream()
                .map(charcter->{
                    CharacterDTO characterDTO = new CharacterDTO(
                            UUID.randomUUID().toString(),
                            charcter.name(),
                            charcter.age(),
                            charcter.profession());
                    return characterDTO;
                }).toList();

    }
}
