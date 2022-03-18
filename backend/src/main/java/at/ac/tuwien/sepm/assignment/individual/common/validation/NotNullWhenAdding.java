package at.ac.tuwien.sepm.assignment.individual.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validator used to validate if that property is not null when the object is provided in a POST endpoint call.
 * See {@link NotNullWhenAddingValidator } for the implementation.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { NotNullWhenAddingValidator.class })
public @interface NotNullWhenAdding {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
