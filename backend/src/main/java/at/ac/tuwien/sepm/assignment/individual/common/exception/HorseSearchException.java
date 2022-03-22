package at.ac.tuwien.sepm.assignment.individual.common.exception;

import java.util.HashMap;
import java.util.Map;

public class HorseSearchException extends RuntimeException {
    private final Map<String, String> errors = new HashMap<>();

    public void putError(String fieldName, String message) {
        var msg = errors.containsKey(fieldName) ? (errors.get(fieldName) + " ") : "";
        errors.put(fieldName, msg + message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
