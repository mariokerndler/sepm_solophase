package at.ac.tuwien.sepm.assignment.individual.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *  A {@link java.lang.RuntimeException} that gets thrown if something requested does not exist.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) { super(msg); }
}
