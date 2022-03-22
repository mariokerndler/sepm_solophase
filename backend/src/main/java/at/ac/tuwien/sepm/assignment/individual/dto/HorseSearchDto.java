package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.common.validation.HorseSearch;
import at.ac.tuwien.sepm.assignment.individual.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@HorseSearch
public class HorseSearchDto {
    private Integer limit;

    private String name;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate bornAfter;

    private Gender gender;

    public boolean isEmpty() {
        return limit == null && name == null && description == null && bornAfter == null && gender == null;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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

    public LocalDate getBornAfter() {
        return bornAfter;
    }

    public void setBornAfter(LocalDate bornAfter) {
        this.bornAfter = bornAfter;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "HorseSearchDto{" +
                "limit=" + limit +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", bornAfter=" + bornAfter +
                ", gender=" + gender +
                '}';
    }
}
