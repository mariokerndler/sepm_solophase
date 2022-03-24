package at.ac.tuwien.sepm.assignment.individual.randomdata;

import at.ac.tuwien.sepm.assignment.individual.dto.AddUpdateHorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public final class RandomHorseGenerator {
    private static final Logger log = LoggerFactory.getLogger(RandomHorseGenerator.class);

    public static HorseDto createRandomHorseDto() {
        log.trace("calling createRandomHorseDto() ...");
        var random = new Random();
        return new HorseDto(
                Math.abs(random.nextLong()),
                createRandomString(),
                createRandomString(),
                createRandomBirthdate(),
                random.nextBoolean() ? Gender.MALE : Gender.FEMALE,
                RandomOwnerGenerator.createRandomOwnerDto(),
                null,
                null
        );
    }

    public static Horse createRandomHorse() {
        log.trace("calling createRandomHors() ...");
        var horse = new Horse();
        var random = new Random();
        horse.setId(Math.abs(random.nextLong()));
        horse.setName(createRandomString());
        horse.setDescription(createRandomString());
        horse.setBirthdate(createRandomBirthdate());
        horse.setGender(randomGender());
        return horse;
    }

    public static AddUpdateHorseDto createRandomAddUpdateHorseDto() {
        log.trace("calling createRandomAddUpdateHorseDto() ...");
        var addUpdateHorseDto = new AddUpdateHorseDto();
        addUpdateHorseDto.setName(createRandomString());
        addUpdateHorseDto.setDescription(createRandomString());
        addUpdateHorseDto.setBirthdate(createRandomBirthdate());
        addUpdateHorseDto.setGender(randomGender());
        return addUpdateHorseDto;
    }

    private static LocalDate createRandomBirthdate() {
        log.trace("calling createRandomBirthdate() ...");
        return LocalDate.now().minusDays(new Random().nextInt(10 * 365));
    }

    private static String createRandomString() {
        log.trace("calling createRandomString() ...");
        return UUID.randomUUID().toString();
    }

    private static Gender randomGender() {
        return new Random().nextBoolean() ? Gender.MALE : Gender.FEMALE;
    }
}
