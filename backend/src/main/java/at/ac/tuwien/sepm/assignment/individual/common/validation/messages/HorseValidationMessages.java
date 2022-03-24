package at.ac.tuwien.sepm.assignment.individual.common.validation.messages;

/**
 * A container for all horse validation messages. For easier management.
 */
public final class HorseValidationMessages {
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_DESCRIPTION_LENGTH = 500;

    public static final String NO_NAME_MESSAGE = "Every horse must have a name. The name has to consist of at least one character.";

    public static final String NAME_TOO_LONG_MESSAGE = "The horse name has to be shorter than " + MAX_NAME_LENGTH + " characters.";

    public static final String INVALID_NAME_MESSAGE = "The horse name may only contain the following characters: A-Z, a-z, 0-9, Ä, Ö, Ü, ä, ö, ü, -, ' '.";

    public static final String DESCRIPTION_TOO_LONG_MESSAGE = "The horse description has to be shorter than " + MAX_DESCRIPTION_LENGTH + " characters.";

    public static final String NO_BIRTHDAY_MESSAGE = "Every horse must have a birthday.";

    public static final String INVALID_BIRTHDAY_MESSAGE = "The birthday of the horse can't be in the future.";

    public static final String NO_GENDER_MESSAGE = "Every horse must have a gender.";

    public static final String OWNER_NEGATIVE_MESSAGE = "The horse ownerId cant be negative.";

    public static final String DAM_SIRE_NEGATIVE_MESSAGE = "Id cant be negative.";

    public static final String INVALID_DAM_GENDER_MESSAGE = "The dam must be a female.";

    public static final String INVALID_SIRE_GENDER_MESSAGE = "The sire must be a male.";

    public static final String INVALID_DAM_BIRTHDATE_MESSAGE = "The dam must be older than the horse itself.";

    public static final String INVALID_SIRE_BIRTHDATE_MESSAGE = "The sire must be older than the horse itself.";

    public static final String INVALID_LIMIT_MESSAGE = "The limit must be positive.";

    public static final String INVALID_OWNERID_MESSAGE = "The owner id must be positive.";
}
