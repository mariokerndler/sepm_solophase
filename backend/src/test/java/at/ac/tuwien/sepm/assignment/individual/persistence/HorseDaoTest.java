package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.time.LocalDate;
import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.enums.Gender;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class HorseDaoTest {

  @Autowired
  HorseDao horseDao;

  @Test
  public void getAllReturnsAllStoredHorses() {
    List<Horse> horses = horseDao.getAll();
    assertThat(horses.size()).isEqualTo(3);
    assertThat(horses.get(0).getId()).isEqualTo(-3);
    assertThat(horses.get(0).getName()).isEqualTo("Lilly");

    assertThat(horses.get(1).getId()).isEqualTo(-2);
    assertThat(horses.get(1).getName()).isEqualTo("Alex");

    assertThat(horses.get(2).getId()).isEqualTo(-1);
    assertThat(horses.get(2).getName()).isEqualTo("Bella");
  }

  @Test
  public void getHorseByIdForExistingId() {
    Horse horse = horseDao.getById(-1L);
    assertThat(horse.getId()).isEqualTo(-1);
    assertThat(horse.getName()).isEqualTo("Bella");
    assertThat(horse.getDescription()).isEqualTo("description 1");
    assertThat(horse.getBirthdate()).isEqualTo(LocalDate.of(2000, 1, 31));
    assertThat(horse.getGender()).isEqualTo(Gender.FEMALE);
  }

  @Test
  public void insertNewHorseAndRetrieveIt() {
    var id = 1L;
    var name = "test";
    var gender = Gender.MALE;
    var currentDate = LocalDate.now();
    var description = "test description";

    var horse = new Horse();
    horse.setId(id);
    horse.setName(name);
    horse.setGender(gender);
    horse.setBirthdate(currentDate);
    horse.setDescription(description);

    var createdHorse = horseDao.create(horse);
    assertThat(createdHorse.getId()).isEqualTo(id);
    assertThat(createdHorse.getName()).isEqualTo(name);
    assertThat(createdHorse.getGender()).isEqualTo(gender);
    assertThat(createdHorse.getBirthdate()).isEqualTo(currentDate);
    assertThat(createdHorse.getDescription()).isEqualTo(description);

    var searchedHorse = horseDao.getById(id);
    assertThat(searchedHorse.getId()).isEqualTo(id);
    assertThat(searchedHorse.getName()).isEqualTo(name);
    assertThat(searchedHorse.getGender()).isEqualTo(gender);
    assertThat(searchedHorse.getBirthdate()).isEqualTo(currentDate);
    assertThat(searchedHorse.getDescription()).isEqualTo(description);
  }

  @Test
  public void getHorseByIdShouldFailForNonExistingId() {
    assertThatThrownBy(() -> horseDao.getById(0L)).isInstanceOf(NotFoundException.class);
  }
}
