package at.ac.tuwien.sepm.assignment.individual.common.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullWhenAddingValidator implements ConstraintValidator<NotNullWhenAdding, Object> {
    private final HttpServletRequest request;
    private static final Logger log = LoggerFactory.getLogger(NotNullWhenAddingValidator.class);

    public NotNullWhenAddingValidator(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("calling isValid() ...");
        return request == null || !(request.getMethod().equals(RequestMethod.POST.name()) && o == null);
    }
}
