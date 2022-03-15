package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.util.List;

/**
 * Service for working with horses.
 */
public interface HorseService {
    /**
     * Returns all {@link Horse horses} stored in the system.
     *
     * @return A list of all {@link Horse horses}.
     */
    List<HorseDto> getHorses();

    /**
     * Retrieves an existing {@link Horse horse} with the given {@link Horse#getId() id}
     *
     * @param id The {@link Horse#getId() id} of the {@link Horse} that will be retrieved.
     *
     * @return The {@link HorseDto} representing the specified {@link Horse}.
     */
    HorseDto getHorseById(Long id);

    /**
     * Creates a new {@link Horse horse}.
     *
     * @param dto The {@link HorseDto} that will be created.
     *
     * @return A {@link HorseDto} representing the newly created {@link Horse}.
     */
    HorseDto createHorse(HorseDto dto);
}
