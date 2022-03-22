package at.ac.tuwien.sepm.assignment.individual.common.validation;

import at.ac.tuwien.sepm.assignment.individual.common.exception.HorseRelationshipException;
import at.ac.tuwien.sepm.assignment.individual.dto.AddUpdateHorseDto;
import at.ac.tuwien.sepm.assignment.individual.enums.Gender;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HorseRelationshipsValidator implements ConstraintValidator<HorseRelationships, AddUpdateHorseDto> {
    private static final Logger log = LoggerFactory.getLogger(HorseRelationshipsValidator.class);

    private final HorseDao dao;

    public HorseRelationshipsValidator(HorseDao horseDao) {
        this.dao = horseDao;
    }

    @Override
    public boolean isValid(AddUpdateHorseDto addUpdateHorseDto, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("calling isValid() ...");

        var dam = dao.findById(addUpdateHorseDto.getDamId());
        var sire = dao.findById(addUpdateHorseDto.getSireId());
        var birthdate = addUpdateHorseDto.getBirthdate();
        var exception = new HorseRelationshipException();

        boolean validDamGender = true;
        boolean validDamBirthdate = true;
        if(dam.isPresent()) {
            var damEntity = dam.get();
            validDamGender = damEntity.getGender().equals(Gender.FEMALE);
            validDamBirthdate = damEntity.getBirthdate().isBefore(birthdate);
        }

        boolean validSireGender = true;
        boolean validSireBirthdate = true;
        if(sire.isPresent()) {
            var sireEntity = sire.get();
            validSireGender = sireEntity.getGender().equals(Gender.MALE);
            validSireBirthdate = sireEntity.getBirthdate().isBefore(birthdate);
        }

        if(validDamGender && validDamBirthdate && validSireGender && validSireBirthdate) {
            return true;
        }

        addValidationError(validDamGender, "damId", HorseValidationMessages.INVALID_DAM_GENDER_MESSAGE, exception);
        addValidationError(validDamBirthdate, "damId", HorseValidationMessages.INVALID_DAM_BIRTHDATE_MESSAGE, exception);
        addValidationError(validSireGender, "sireId", HorseValidationMessages.INVALID_SIRE_GENDER_MESSAGE, exception);
        addValidationError(validSireBirthdate, "sireId", HorseValidationMessages.INVALID_SIRE_BIRTHDATE_MESSAGE, exception);

        throw exception;
    }

    private void addValidationError(boolean valid, String propertyName, String message, HorseRelationshipException exception) {
        log.trace("calling addValidationError() ...");
        if(!valid) {
            exception.putError(propertyName, message);
        }
    }
}
