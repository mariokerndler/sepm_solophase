package at.ac.tuwien.sepm.assignment.individual.randomdata;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

public final class RandomOwnerGenerator {
    private static final Logger log = LoggerFactory.getLogger(RandomOwnerGenerator.class);

    public static Owner createRandomOwner() {
        log.trace("calling createRandomOwner() ...");
        var owner = new Owner();
        var random = new Random();
        owner.setId(Math.abs(random.nextLong()));
        owner.setFirstname(createRandomString());
        owner.setSurname(createRandomString());
        owner.setEmail(createRandomEmail());
        return owner;
    }

    public static OwnerDto createRandomOwnerDto() {
        log.trace("calling createRandomOwnerDto() ...");
        var random = new Random();

        return new OwnerDto(
                Math.abs(random.nextLong()),
                createRandomString(),
                createRandomString(),
                createRandomEmail()
        );
    }

    private static String createRandomString() {
        log.trace("calling createRandomString() ...");
        return UUID.randomUUID().toString();
    }

    private static String createRandomEmail() {
        log.trace("calling createRandomEmail() ...");

        var allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        var salt = new StringBuilder();
        var rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * allowedChars.length());
            salt.append(allowedChars.charAt(index));
        }
        return salt + "@testdata.com";
    }
}
