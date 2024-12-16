package com.example.Asterix.API.Controller;

import com.example.Asterix.API.Model.AsterixCharacter;
import com.example.Asterix.API.Repositorie.CharacterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc

class AsterixCharacterControlleTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllCharactersEmptyList_WhenInitially() throws Exception {
        //Give
        //Whan & Then
        mockMvc.perform(get("/asterix/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void getAllCharacters_WhenCharactersCalled_ShouldReturnAllCharacters() throws Exception {
        AsterixCharacter character1 = new AsterixCharacter("1", "Asterix", 35, "Warrior");
        AsterixCharacter character2 = new AsterixCharacter("2", "Obelix", 35, "Deliveryman");
        characterRepository.save(character1);
        characterRepository.save(character2);

        MvcResult mvcResult =mockMvc.perform(get("/asterix/characters"))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse= mvcResult.getResponse().getContentAsString();
        AsterixCharacter [] asterixCharacters=objectMapper.readValue(jsonResponse, AsterixCharacter[].class);
        assertThat(asterixCharacters).hasSize(2);

    }


    @Test
    void getBy_Id_ShouldReturn_Character_WhenCalledWith_ValidId() throws Exception {
        AsterixCharacter character =new AsterixCharacter(
                "1",
                "Omar",
                30,
                "Programmer");
        characterRepository.save(character);
        mockMvc.perform(get("/asterix/characters/"+character.id()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                         
                                         {
                                         "id":"1",
                                         "name":"Omar",
                                         "age":30,
                                         "profession":"Programmer"
                                         }

                                         """));

    }

    @Test
    void shouldCreateCharacter() throws Exception {
//        AsterixCharacter character1 = new AsterixCharacter("1", "Asterix", 35, "Warrior");
//        mockMvc.perform(post("/asterix/characters")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(character1)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("Asterix"));
        mockMvc.perform(post("/asterix/characters")
        .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id":"1",
                                             "name":"Omar",
                                                              "age":30,
                                                                               "profession":"Programmer"
                        }
                 """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                {
                                             "name":"Omar",
                                                              "age":30,
                                                                               "profession":"Programmer"
                }
                """))
                .andExpect(jsonPath("$.id").isNotEmpty());

    }

    @Test
    void shouldUpdateCharacter() throws Exception {

        MvcResult postResult = mockMvc.perform(post("/asterix/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AsterixCharacter(null, "Troubadix", 25, "Bard"))))
                .andExpect(status().isOk())
                .andReturn();

        AsterixCharacter createdCharacter = objectMapper.readValue(postResult.getResponse().getContentAsString(), AsterixCharacter.class);

        AsterixCharacter updatedCharacter = new AsterixCharacter(createdCharacter.id(), "Troubadix", 30, "Bard");

        mockMvc.perform(put("/asterix/characters/" + createdCharacter.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCharacter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdCharacter.id()));

    }




}