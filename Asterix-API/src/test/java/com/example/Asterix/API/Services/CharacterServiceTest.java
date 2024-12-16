package com.example.Asterix.API.Services;

import com.example.Asterix.API.Dto.CharacterDTO;
import com.example.Asterix.API.Model.AsterixCharacter;
import com.example.Asterix.API.Repositorie.CharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CharacterServiceTest {

    private final CharacterRepository characterRepository = mock(CharacterRepository.class);
    private final IdService idService = mock(IdService.class);


    @Test
    void getAllCharacters() {
        //Given
        List<CharacterDTO> expected = Collections.emptyList();
        CharacterService characterService = new CharacterService(characterRepository,idService);

        //When
        List<CharacterDTO> actual = characterService.getAllCharacter();

        //Then
        Assertions.assertEquals(expected,actual);

    }

    @Test
    void getCharacterById() {
        //Given
        AsterixCharacter character = new AsterixCharacter("1","Omar",29,"TTTT");
        CharacterDTO characterDTO = new CharacterDTO(character.id(),character.name(),character.age(),character.profession());
        CharacterService characterService = new CharacterService(characterRepository,idService);
        when(characterRepository.findById(character.id())).thenReturn(Optional.of(character));

        //When
        CharacterDTO actual= characterService.getCharacterById(character.id());

        //Then
        Assertions.assertEquals(characterDTO,actual);
    }

    @Test
    void updateCharacter() {

        //Given
        AsterixCharacter character = new AsterixCharacter("1","Omar",29,"TTTT");
        AsterixCharacter expected= new AsterixCharacter(
                character.id(),character.name(),character.age(),character.profession());
        CharacterService characterService = new CharacterService(characterRepository,idService);
        when(characterRepository.existsById(character.id())).thenReturn(true);
        when(characterRepository.findById(character.id())).thenReturn(Optional.of(character));
        //When
        AsterixCharacter actual= characterService.updateCharacter(character,character.id());
        //
        Assertions.assertEquals(expected,actual);
        verify(characterRepository).save(character);

    }


    @Test
    void deleteCharacter() {

        //Given
        CharacterService characterService = new CharacterService(characterRepository,idService);
        AsterixCharacter character = new AsterixCharacter("1","Omar",29,"TTTT");
        AsterixCharacter expected= new AsterixCharacter(
                character.id(),character.name(),character.age(),character.profession());
        when(characterRepository.existsById(character.id())).thenReturn(true);
        characterService.deleteCharacter(character.id());
        verify(characterRepository).deleteById(character.id());

    }

    @Test
    void createCharacter_Should_return_IdRandom_And_Save_Character() {


        //Given
        String generatedId = "1234";
        CharacterDTO character = new CharacterDTO(null, "Omar", 29, "TTTT"); // ID is null here
        AsterixCharacter expected = new AsterixCharacter(generatedId, character.name(), character.age(), character.profession());

        // Mock the ID generation and save behavior
        when(idService.generateId()).thenReturn(generatedId);
        when(characterRepository.save(any(AsterixCharacter.class))).thenReturn(expected);

        //When
        CharacterService characterService = new CharacterService(characterRepository, idService);
        AsterixCharacter result = characterService.saveCharacter(character);

        //Then
        assertEquals(expected, result);
        verify(idService).generateId();
        verify(characterRepository).save(any(AsterixCharacter.class));


    }

}