package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.enums.Gender;

import java.time.LocalDate;

public class Horse {
    private Long id;
    private String name;
    private String description;
    private LocalDate birthdate;
    private Gender gender;
    private Long ownerId;

    private Horse dam;
    private Long damId;

    private Horse sire;
    private Long sireId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Horse getDam() {
        return dam;
    }

    public void setDam(Horse dam) {
        this.dam = dam;
    }

    public Long getDamId() {
        return damId;
    }

    public void setDamId(Long damId) {
        this.damId = damId;
    }

    public Horse getSire() {
        return sire;
    }

    public void setSire(Horse sire) {
        this.sire = sire;
    }

    public Long getSireId() {
        return sireId;
    }

    public void setSireId(Long sireId) {
        this.sireId = sireId;
    }
}
