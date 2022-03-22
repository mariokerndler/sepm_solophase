package at.ac.tuwien.sepm.assignment.individual.common.validation;

import at.ac.tuwien.sepm.assignment.individual.common.exception.HorseSearchException;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.dto.validation.HorseValidationMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class HorseSearchValidator implements ConstraintValidator<HorseSearch, HorseSearchDto> {

    private static final Logger log = LoggerFactory.getLogger(HorseSearchValidator.class);

    @Override
    public boolean isValid(HorseSearchDto searchDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean validName = true;
        boolean validDescription = true;
        boolean validBornAfter = true;
        boolean validLimit = true;

        if(searchDto.getName() != null && !searchDto.getName().isEmpty()) {
            validName = (!searchDto.getName().isBlank() || searchDto.getName().length() <= HorseValidationMessages.MAX_NAME_LENGTH);
        }

        if(searchDto.getDescription() != null && !searchDto.getDescription().isEmpty()) {
            validDescription = (!searchDto.getDescription().isBlank() || searchDto.getName().length() <= HorseValidationMessages.MAX_DESCRIPTION_LENGTH);
        }

        if(searchDto.getBornAfter() != null) {
            validBornAfter = !(searchDto.getBornAfter().isAfter(LocalDate.now()));
        }

        if(searchDto.getLimit() != null) {
            validLimit = searchDto.getLimit() >= 0;
        }

        if(validBornAfter && validName && validDescription && validLimit) {
            return true;
        }

        HorseSearchException exception = new HorseSearchException();

        addSearchValidationError(validName, HorseValidationMessages.NAME_TOO_LONG_MESSAGE, exception);
        addSearchValidationError(validDescription, HorseValidationMessages.DESCRIPTION_TOO_LONG_MESSAGE, exception);
        addSearchValidationError(validBornAfter, HorseValidationMessages.INVALID_BIRTHDAY_MESSAGE, exception);
        addSearchValidationError(validLimit, HorseValidationMessages.INVALID_LIMIT_MESSAGE, exception);

        throw exception;
    }

    private void addSearchValidationError(boolean valid, String message, HorseSearchException exception) {
        log.trace("calling addValidationError() ...");
        if(!valid) {
            exception.putError("search", message);
        }
    }
}
