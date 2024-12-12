package com.example.Asterix.API.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asterix")
public class AsterixControlle {

    @Autowired
    private CharacterRepository characterRepository;


    @GetMapping("/characters")
    public List<asterixCharacter> getAsterixCharacters() {

       return characterRepository.findAll();
    }

}
