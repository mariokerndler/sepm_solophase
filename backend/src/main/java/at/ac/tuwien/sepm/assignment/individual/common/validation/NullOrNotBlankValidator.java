package at.ac.tuwien.sepm.assignment.individual.common.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

    private static final Logger log = LoggerFactory.getLogger(NullOrNotBlankValidator.class);

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("calling isValid() ...");
        return s == null || !s.isBlank();
    }
}
