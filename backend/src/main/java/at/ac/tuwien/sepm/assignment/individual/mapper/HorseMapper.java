package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.AddUpdateHorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A mapper that handles the conversion between a {@link Horse horse} and either {@link AddUpdateHorseDto} or {@link HorseDto}
 */
@Component
public class HorseMapper {

    private static final Logger log = LoggerFactory.getLogger(HorseMapper.class);

    public HorseDto entityToDto(Horse horse) {
        log.trace("calling entityToDto() ...");
        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthdate(), horse.getGender(), horse.getOwner());
    }

    public Horse dtoToEntity(AddUpdateHorseDto dto) {
        log.trace("calling dtoToEntity() ...");

        Horse horse = new Horse();
        horse.setName(dto.getName());
        horse.setDescription(dto.getDescription());
        horse.setBirthdate(dto.getBirthdate());
        horse.setGender(dto.getGender());
        horse.setOwner(dto.getOwnerId());
        return horse;
    }

    public void updateFromDto(Horse horse, AddUpdateHorseDto updateHorseDto) {
        log.trace("calling updateFromDto() ...");

        horse.setName(updateHorseDto.getName());
        horse.setDescription(updateHorseDto.getDescription());
        horse.setBirthdate(updateHorseDto.getBirthdate());
        horse.setGender(updateHorseDto.getGender());
        horse.setOwner(updateHorseDto.getOwnerId());
    }
}