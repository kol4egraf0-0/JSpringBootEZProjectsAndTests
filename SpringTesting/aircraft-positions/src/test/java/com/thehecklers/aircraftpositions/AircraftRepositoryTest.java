package com.thehecklers.aircraftpositions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class AircraftRepositoryTest {
    @Autowired
    private AircraftRepository aircraftRepository;

    private Aircraft ac1, ac2;

    @BeforeEach
    void setUp() {
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        ac2 = new Aircraft(2L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8560963, -104.6759263, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        aircraftRepository.saveAll(List.of(ac1, ac2));
    }

    @AfterEach
    void tearDown() {
        aircraftRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        assertEquals(List.of(ac1, ac2), aircraftRepository.findAll());
    }

    @Test
    void testFindById(){
        assertEquals(Optional.of(ac1), aircraftRepository.findById(ac1.getId()));
        assertEquals(Optional.of(ac2), aircraftRepository.findById(ac2.getId()));
    }
}
