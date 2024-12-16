package com.example.Asterix.API.Dto;

import com.example.Asterix.API.Services.IdService;

public record CharacterDTO(
        String id,
        String name,
        int age,
        String profession
) {
}
