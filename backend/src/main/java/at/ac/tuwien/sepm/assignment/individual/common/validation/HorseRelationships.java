package at.ac.tuwien.sepm.assignment.individual.common.validation;

import at.ac.tuwien.sepm.assignment.individual.dto.AddUpdateHorseDto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validator for {@link AddUpdateHorseDto}.
 * Validates the parents (if given) for the horse, by checking their gender and birthdate.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HorseRelationshipsValidator.class)
public @interface HorseRelationships {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
