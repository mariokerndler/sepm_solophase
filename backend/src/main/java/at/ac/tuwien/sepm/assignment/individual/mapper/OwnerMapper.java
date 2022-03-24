package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.AddOwnerDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A mapper that handles the conversion between a {@link Owner owner} and {@link AddOwnerDto}
 */
@Component
public class OwnerMapper {
    private static final Logger log = LoggerFactory.getLogger(OwnerMapper.class);

    public OwnerDto entityToDto(Owner owner) {
        log.trace("calling entityToDto() ...");
        return new OwnerDto(owner.getId(), owner.getFirstname(), owner.getSurname(), owner.getEmail());
    }

    public Owner dtoToEntity(AddOwnerDto addOwnerDto) {
        log.trace("calling dtoToEntity() ...");

        Owner owner = new Owner();
        owner.setFirstname(addOwnerDto.getFirstname());
        owner.setSurname(addOwnerDto.getSurname());
        owner.setEmail(addOwnerDto.getEmail());
        return owner;
    }
}
