package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.common.validation.messages.OwnerValidationMessages;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Contains all properties needed to create a new owner.
 * Uses annotations to validate the properties.
 */
public class AddOwnerDto {

    @Size(max = OwnerValidationMessages.MAX_CHARACTERS, message = OwnerValidationMessages.TOO_LONG_FIRSTNAME_MESSAGE)
    @NotBlank(message = OwnerValidationMessages.MISSING_FIRSTNAME_MESSAGE)
    @Pattern(regexp = "[ÄÖÜA-Z][äöüa-zA-Z]*", message = OwnerValidationMessages.INVALID_FIRSTNAME_MESSAGE)
    private String firstname;

    @Size(max = OwnerValidationMessages.MAX_CHARACTERS, message = OwnerValidationMessages.TOO_LONG_SURNAME_MESSAGE)
    @NotBlank(message = OwnerValidationMessages.MISSING_SURNAME_MESSAGE)
    @Pattern(regexp = "[ÄÖÜa-zA-z]+([ '-][äöüa-zA-Z]+)*", message = OwnerValidationMessages.INVALID_SURNAME_MESSAGE)
    private String surname;

    @Pattern(regexp = OwnerValidationMessages.EMAIL_REGEX, message = OwnerValidationMessages.INVALID_EMAIL_MESSAGE)
    private String email;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
