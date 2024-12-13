package com.example.Asterix.API.Controller;

import com.example.Asterix.API.Model.AsterixCharacter;
import com.example.Asterix.API.Repositorie.CharacterRepository;
import com.example.Asterix.API.Services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asterix/characters")
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


    @GetMapping("/{id}")
    public AsterixCharacter getAsterixCharacterPyId(@PathVariable String id) {

        return characterService.getCharacterById(id);
    }

    @PostMapping
    public AsterixCharacter addAsterixCharacter(@RequestBody AsterixCharacter character) {
        return characterService.saveCharacter(character);
    }


    @PutMapping("/{id}")
    public  AsterixCharacter updateAsterixCharacter(@PathVariable String id, @RequestBody AsterixCharacter updateCharacter) {
     return characterService.updateCharacter(id,updateCharacter);
    }


    @DeleteMapping("/{id}")
    public void deleteAsterixCharacter(@PathVariable String id) {
        characterService.deleteupdateCharacter(id);
    }

    @GetMapping("/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        return characterService.getAverageAgeByProfession(profession);
    }


}
