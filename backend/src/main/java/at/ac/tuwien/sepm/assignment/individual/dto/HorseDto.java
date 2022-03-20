package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Gender;

import java.time.LocalDate;

/**
 * Class for Horse DTOs
 * Contains all common properties
 */
public record HorseDto(Long id, String name, String description, LocalDate birthdate, Gender gender, Long ownerId, HorseDto damId, HorseDto sireId) {

}
