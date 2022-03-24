package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.AddOwnerDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;

import java.util.List;

/**
 * Service for working with owner.
 */
public interface OwnerService {

    /**
     * Returns all {@link Owner owners} stored in the system.
     *
     * @return A list of all {@link Owner owners}.
     */
    List<OwnerDto> getOwners();

    /**
     * Retrieves an existing {@link Owner owner} with the given {@link Owner#getId() id}
     *
     * @param id The {@link Owner#getId() id} of the {@link Owner} that will be retrieved.
     *
     * @return The {@link OwnerDto} representing the specified {@link Owner}.
     */
    OwnerDto getOwnerById(Long id);

    /**
     * Creates a new {@link Owner owner}.
     *
     * @param addOwnerDto The {@link OwnerDto} that will be created.
     *
     * @return A {@link OwnerDto} representing the newly created {@link Owner}.
     */
    OwnerDto createOwner(AddOwnerDto addOwnerDto);
}
