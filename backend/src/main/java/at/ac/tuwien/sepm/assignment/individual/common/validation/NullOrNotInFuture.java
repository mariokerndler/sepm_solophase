package at.ac.tuwien.sepm.assignment.individual.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validator used to validate if the LocalDate property is in the future.
 * See {@link NullOrNotInFutureValidator } for the implementation.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { NullOrNotInFutureValidator.class })
public @interface NullOrNotInFuture {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
