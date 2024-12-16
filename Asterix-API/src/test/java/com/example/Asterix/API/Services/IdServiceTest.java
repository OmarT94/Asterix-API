package com.example.Asterix.API.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.mockStatic;

public class IdServiceTest {

    @Test
    void generateId_ShouldReturn_UUID() {
        String expected = "123e4567-e89b-12d3-a456-426614174000"; // Valid UUID format
        UUID uuid =UUID.fromString(expected);

        try (MockedStatic<UUID> mockedUUID = mockStatic(UUID.class)) {
            // Mock UUID.randomUUID() to return the expected UUID
            mockedUUID.when(UUID::randomUUID).thenReturn(uuid);

            // Act
            IdService idService = new IdService();
            String actual = idService.generateId();

            // Assert
            Assertions.assertEquals(expected, actual); // Ensure generated ID matches mocked UUID
            mockedUUID.verify(UUID::randomUUID); // Verify UUID.randomUUID() was called exactly once
        }
    }

}
