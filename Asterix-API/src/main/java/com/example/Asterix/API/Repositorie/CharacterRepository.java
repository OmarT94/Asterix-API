package com.example.Asterix.API.Repositorie;

import com.example.Asterix.API.Model.AsterixCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharacterRepository extends MongoRepository<AsterixCharacter, String> {

    List<AsterixCharacter> findByName(String name);

    List<AsterixCharacter> findByAge(Integer age);

    List<AsterixCharacter> findByProfession(String profession);

    @Query(value = "{ 'profession': :#{#profession} }", fields = "{ 'age': 1 }")
    List<AsterixCharacter> findAgesByProfession(@Param("profession") String profession);
}
