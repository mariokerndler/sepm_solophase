package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.common.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.dto.AddUpdateHorseDto;
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
     * @param numberOfGeneration Number of generations to include.
     *
     * @return The {@link HorseDto} representing the specified {@link Horse}.
     */
    HorseDto getHorseById(Long id, int numberOfGeneration);

    /**
     * Creates a new {@link Horse horse}.
     *
     * @param dto The {@link HorseDto} that will be created.
     *
     * @return A {@link HorseDto} representing the newly created {@link Horse}.
     */
    HorseDto createHorse(AddUpdateHorseDto dto);

    /**
     * Updates an existing {@link Horse horse} from an {@link HorseDto}.
     *
     * @param id  The {@link Horse#getId() id} of the {@link Horse horse} that is to be updated.
     * @param dto The {@link HorseDto} that contains the information with which the {@link Horse}
     *            should be updated. This information must already be validated.
     *
     * @return A {@link HorseDto} representing the newly updated {@link Horse}.
     */
    HorseDto updateHorse(Long id, AddUpdateHorseDto dto);

    /**
     * Deletes the {@link Horse} with the given {@link Horse#getId() id}.
     *
     * @param id The {@link Horse#getId() id} of the {@link Horse} to delete.
     *
     * @throws NotFoundException When no {@link Horse} with the given id exists.
     */
    void deleteHorseById(Long id);
}
