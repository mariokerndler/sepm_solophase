package at.ac.tuwien.sepm.assignment.individual.common.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class NullOrNotInFutureValidator implements ConstraintValidator<NullOrNotInFuture, LocalDate> {

    private static final Logger log = LoggerFactory.getLogger(NullOrNotInFutureValidator.class);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("calling isValid() ...");
        return localDate == null || !localDate.isAfter(LocalDate.now());
    }
}
