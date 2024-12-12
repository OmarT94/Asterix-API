package com.example.Asterix.API.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asterix")
public class AsterixControlle {

    @Autowired
    private CharacterRepository characterRepository;

    private final CharacterService characterService;

    public AsterixControlle(CharacterService characterService) {
        this.characterService = characterService;
    }


    //    @GetMapping("/characters")
//    public List<AsterixCharacter> getAsterixCharacters() {
//
//       return characterRepository.findAll();
//    }
    @GetMapping
    public List<AsterixCharacter> getFilteredCharacters(
        @RequestParam(required = false)String name,
        @RequestParam(required = false)Integer age,
        @RequestParam(required = false)String profession
    ) {
        if(name!=null){
            return characterRepository.findByName(name);
        }else if(age != null){
            return characterRepository.findByAge(age);
        }else if(profession!=null){
            return characterRepository.findByProfession(profession);
        }else{
            return characterRepository.findAll();
        }
    }


    @GetMapping("/character/{id}")
    public AsterixCharacter getAsterixCharacterPyId(@PathVariable String id) {

        return characterRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Character with id " + id + " not found."));
    }

    @PostMapping
    public AsterixCharacter addAsterixCharacter(@RequestBody AsterixCharacter character) {
        return characterRepository.save(character);
    }


    @PutMapping("/{id}")
    public  AsterixCharacter updateAsterixCharacter(@PathVariable String id, @RequestBody AsterixCharacter updateCharacter) {
        Optional<AsterixCharacter> existingCharacterOtional = characterRepository.findById(id);
        if (existingCharacterOtional.isPresent()) {
            AsterixCharacter existingharacter = existingCharacterOtional.get();
            AsterixCharacter updatedEntity= new AsterixCharacter(
                    existingharacter.id(),
                    updateCharacter.name(),
                    updateCharacter.age(),
                    updateCharacter.profession()
            );
            return characterRepository.save(updatedEntity);

        }else {
            throw new RuntimeException("Character with id " + id + " not found.");
        }
    }


    @DeleteMapping("/{id}")
    public void deleteAsterixCharacter(@PathVariable String id) {
        if (characterRepository.findById(id).isPresent()) {
            characterRepository.deleteById(id);
        }else {
            throw new RuntimeException("Character with id " + id + " not found.");
        }
    }

    @GetMapping("/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        return characterService.getAverageAgeByProfession(profession);
    }


}
