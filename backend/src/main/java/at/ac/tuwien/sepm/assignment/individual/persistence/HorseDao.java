package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

/**
 * Data Access Object for horses.
 * Implements access functionality to the application's persistent data store regarding horses.
 */
public interface HorseDao {
    /**
     * Get all {@link Horse horses} stored in the persistent data store.
     *
     * @return A list of all {@link Horse horses} stored in the persistent data store.
     *
     * @throws PersistenceException When an error occurred during the data access.
     */
    List<Horse> getAll();

    /**
     * Retrieves the {@link Horse horse} with the given {@link Horse#getId()} id} from the persistent data store.
     *
     * @param id The {@link Horse#getId()} id} of the {@link Horse horse} to return.
     *
     * @return The specified {@link Horse}.
     *
     * @throws NotFoundException When no {@link Horse} with the given id exists.
     */
    Horse getById(Long id);

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
    void deleteHorseById(Long id);
}
