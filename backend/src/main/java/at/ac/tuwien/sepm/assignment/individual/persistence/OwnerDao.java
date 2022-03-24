package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;

import java.util.List;

/**
 * Data Access Object for owner.
 * Implements access functionality to the application's persistent data store regarding owners.
 */
public interface OwnerDao {

    /**
     * Get all {@link Owner owners} stored in the persistent data store.
     *
     * @return A list of all {@link Owner owners} stored in the persistent data store.
     */
    List<Owner> getAll();

    /**
     * Retrieves the {@link Owner owner} with the given {@link Owner#getId() id} from the persistent data store.
     *
     * @param id The {@link Owner#getId() id} of the {@link Owner owner} to return.
     *
     * @return The specified {@link Owner}.
     */
    Owner getById(Long id);

    /**
     * Create a new {@link Owner owner} in the persistent data store.
     *
     * @param owner The {@link Owner owner} that will be created.
     *
     * @return The newly created {@link Owner owner}.
     */
    Owner create(Owner owner);
}
