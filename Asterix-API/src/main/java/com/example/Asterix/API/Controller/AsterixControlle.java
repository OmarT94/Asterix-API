package com.example.Asterix.API.Controller;

import com.example.Asterix.API.Dto.CharacterDTO;
import com.example.Asterix.API.Model.AsterixCharacter;
import com.example.Asterix.API.Repositorie.CharacterRepository;
import com.example.Asterix.API.Services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CharacterDTO getAsterixCharacterPyId(@PathVariable String id) {

        return characterService.getCharacterById(id);
    }

    @PostMapping
    public AsterixCharacter addAsterixCharacter(@RequestBody CharacterDTO characterDTO) {
        return characterService.saveCharacter(characterDTO);
    }


    @PutMapping("/{id}")
    public  AsterixCharacter updateAsterixCharacter(@RequestBody AsterixCharacter updateCharacter,@PathVariable String id) {
     return characterService.updateCharacter(updateCharacter,id);
    }


    @DeleteMapping("/{id}")
    public void deleteAsterixCharacter(@PathVariable String id) {
        characterService.deleteCharacter(id);
    }

    @GetMapping("/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        return characterService.getAverageAgeByProfession(profession);
    }


}
