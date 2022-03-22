package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.common.validation.HorseRelationships;
import at.ac.tuwien.sepm.assignment.individual.common.validation.NotNullWhenAdding;
import at.ac.tuwien.sepm.assignment.individual.common.validation.NullOrNotBlank;
import at.ac.tuwien.sepm.assignment.individual.common.validation.NullOrNotInFuture;
import at.ac.tuwien.sepm.assignment.individual.common.validation.HorseValidationMessages;
import at.ac.tuwien.sepm.assignment.individual.enums.Gender;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * A container for adding or updating a horse, using Validators to check input.
 */
@HorseRelationships
public class AddUpdateHorseDto {
    @Size(max = HorseValidationMessages.MAX_NAME_LENGTH, message = HorseValidationMessages.NAME_TOO_LONG_MESSAGE)
    @Pattern(regexp = "[ÄÖÜA-Zäöüa-z0-9\\- ]*", message = HorseValidationMessages.INVALID_NAME_MESSAGE)
    @NullOrNotBlank(message = HorseValidationMessages.NO_NAME_MESSAGE)
    @NotNullWhenAdding(message = HorseValidationMessages.NO_NAME_MESSAGE)
    private String name;

    @Size(max = HorseValidationMessages.MAX_DESCRIPTION_LENGTH, message = HorseValidationMessages.DESCRIPTION_TOO_LONG_MESSAGE)
    private String description;

    @NotNullWhenAdding(message = HorseValidationMessages.NO_BIRTHDAY_MESSAGE)
    @NullOrNotInFuture(message = HorseValidationMessages.INVALID_BIRTHDAY_MESSAGE)
    private LocalDate birthdate;

    @NotNullWhenAdding(message = HorseValidationMessages.NO_GENDER_MESSAGE)
    private Gender gender;

    @Min(value = 0, message = HorseValidationMessages.OWNER_NEGATIVE_MESSAGE)
    private Long ownerId;

    @Min(value = 0, message = HorseValidationMessages.DAM_SIRE_NEGATIVE_MESSAGE)
    private Long damId;

    @Min(value = 0, message = HorseValidationMessages.DAM_SIRE_NEGATIVE_MESSAGE)
    private Long sireId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getDamId() {
        return damId;
    }

    public void setDamId(Long damId) {
        this.damId = damId;
    }

    public Long getSireId() {
        return sireId;
    }

    public void setSireId(Long sireId) {
        this.sireId = sireId;
    }
}
