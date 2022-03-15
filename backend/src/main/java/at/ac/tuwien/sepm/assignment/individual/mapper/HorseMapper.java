package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HorseMapper {

    private static final Logger log = LoggerFactory.getLogger(HorseMapper.class);

    public HorseDto entityToDto(Horse horse) {
        log.trace("calling entityToDto() ...");
        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(), horse.getBirthdate(), horse.getGender(), horse.getOwnerId());
    }

    public Horse dtoToEntity(HorseDto dto) {
        log.trace("calling dtoToEntity() ...");

        Horse horse = new Horse();
        horse.setId(dto.id());
        horse.setName(dto.name());
        horse.setDescription(dto.description());
        horse.setBirthdate(dto.birthdate());
        horse.setGender(dto.gender());
        horse.setOwnerId(dto.ownerId());
        return horse;
    }
}