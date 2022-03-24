package at.ac.tuwien.sepm.assignment.individual.common.validation.messages;

/**
 * A container for all owner validation messages. For easier management.
 */
public final class OwnerValidationMessages {
    public static final int MAX_CHARACTERS = 100;

    public static final String MISSING_FIRSTNAME_MESSAGE = "Every owner must have a first name.";

    public static final String MISSING_SURNAME_MESSAGE = "Every owner must have a surname.";

    public static final String INVALID_FIRSTNAME_MESSAGE = "The owner first name may only contain the following characters: A-Z, a-z, Ä, Ö, Ü, ä, ö, ü.";

    public static final String INVALID_SURNAME_MESSAGE = "The owner surname may only contain the following characters: A-Z, a-z, Ä, Ö, Ü, ä, ö, ü, -, ' '.";

    public static final String TOO_LONG_FIRSTNAME_MESSAGE = "The owner first name has to be shorter than " + MAX_CHARACTERS + " characters.";

    public static final String TOO_LONG_SURNAME_MESSAGE = "The owner surname has to be shorter than " + MAX_CHARACTERS + " characters.";

    public static final String INVALID_EMAIL_MESSAGE = "The email address is not valid.";

    // Copied from here: https://www.baeldung.com/java-email-validation-regex
    public static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
}
