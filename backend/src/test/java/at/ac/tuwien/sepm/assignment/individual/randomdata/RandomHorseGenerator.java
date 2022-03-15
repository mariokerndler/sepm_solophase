package at.ac.tuwien.sepm.assignment.individual.randomdata;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
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
                random.nextLong(),
                createRandomString(),
                createRandomString(),
                createRandomBirthdate(),
                random.nextBoolean() ? Gender.MALE : Gender.FEMALE,
                null
        );
    }

    public static HorseDto createRandomHorseDtoWithMissingGender() {
        log.trace("calling createRandomHorseDtoWithMissingGender() ...");
        var random = new Random();
        return new HorseDto(
                random.nextLong(),
                null,
                createRandomString(),
                createRandomBirthdate(),
                null,
                null
        );
    }

    private static LocalDate createRandomBirthdate() {
        log.trace("calling createRandomBirthdate() ...");
        return LocalDate.now().minusDays(new Random().nextInt(10 * 365));
    }

    private static String createRandomString() {
        log.trace("calling createRandomString() ...");
        return UUID.randomUUID().toString();
    }
}
