package at.ac.tuwien.sepm.assignment.individual.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validator used to validate if the string property is either null or not {@link String#isBlank()}.
 * See {@link NullOrNotBlankValidator } for the implementation.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { NullOrNotBlankValidator.class })
public @interface NullOrNotBlank {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
