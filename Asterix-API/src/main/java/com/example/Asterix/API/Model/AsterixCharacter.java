package com.example.Asterix.API.Model;

import org.springframework.data.annotation.Id;

//@Document(collation = "asterixCharacter")
public record AsterixCharacter(@Id String id,
                               String name,
                               int age,
                               String profession) {
}
