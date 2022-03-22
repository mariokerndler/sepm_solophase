package at.ac.tuwien.sepm.assignment.individual.common.validation;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validator used to validate {@link HorseSearchDto}'s
 * See {@link HorseSearchValidator } for the implementation.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HorseSearchValidator.class)
public @interface HorseSearch {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
