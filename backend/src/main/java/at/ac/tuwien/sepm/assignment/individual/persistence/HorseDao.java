package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.common.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for horses.
 * Implements access functionality to the application's persistent data store regarding horses.
 */
public interface HorseDao {
    /**
     * Get all {@link Horse horses} stored in the persistent data store.
     *
     * @return A list of all {@link Horse horses} stored in the persistent data store.
     */
    List<Horse> getAll(HorseSearchDto searchDto);

    /**
     * Retrieves the {@link Horse horse} with the given {@link Horse#getId() id} from the persistent data store.
     *
     * @param id The {@link Horse#getId() id} of the {@link Horse horse} to return.
     *
     * @return The specified {@link Horse}.
     */
    Horse getById(Long id);

    /**
     * Retrieves the {@link Horse horse} with the given {@link Horse#getId() id} from the persistent data store with a certain depth.
     *
     * @param id The {@link Horse#getId() id} of the {@link Horse horse} to return.
     * @param numberOfGenerations The depth that will be fetched.
     *
     * @return The specified {@link Horse}.
     */
    Horse getById(Long id, int numberOfGenerations);

    /**
     * Create a new {@link Horse horse} in the persistent data store.
     *
     * @param horse The {@link Horse horse} that will be created.
     *
     * @return The newly created {@link Horse horse}.
     */
    Horse create(Horse horse);

    /**
     * Updates the {@link Horse horse} in the persistent data store.
     *
     * @param horse The {@link Horse horse} that will be updated.
     *
     * @return The newly updated {@link Horse horse}.
     */
    Horse update(Horse horse);

    /**
     * Deletes the {@link Horse horse} with the given {@link Horse#getId() id} from the persistent data store.
     *
     * @param id The {@link Horse#getId() id} of the {@link Horse horse} which will be deleted.
     *
     * @throws NotFoundException When no {@link Horse} with the given id exists.
     */
    void deleteById(Long id);

    Optional<Horse> findById(Long id);
}
