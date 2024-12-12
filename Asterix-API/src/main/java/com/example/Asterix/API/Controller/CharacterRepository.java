package com.example.Asterix.API.Controller;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<asterixCharacter, String> {
}
