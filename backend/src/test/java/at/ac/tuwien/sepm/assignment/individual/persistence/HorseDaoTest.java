package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.common.exception.HorseSearchException;
import at.ac.tuwien.sepm.assignment.individual.common.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.time.LocalDate;
import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.enums.Gender;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class HorseDaoTest {

  @Autowired
  HorseDao horseDao;

  @Test
  public void getHorseByExistingIdShouldSucceed() {
   assertThat(horseDao.getById(1000001L)).isNotNull();
  }

  @Test
  public void getHorseByNonExistingIdShouldFail() {
    assertThatThrownBy(() -> horseDao.getById(0L)).isInstanceOf(NotFoundException.class);
  }

  @Test
  public void getAllWithoutSearchShouldReturnAllHorses() {
    var horses = horseDao.getAll(null);
    assertThat(horses).isNotNull();
    assertThat(horses.size()).isEqualTo(10);
  }

  @Test
  public void getAllWithSearchShouldReturnCorrectResults() {
    var searchDto = new HorseSearchDto();
    searchDto.setName("a");
    searchDto.setDescription("Description");
    searchDto.setGender(Gender.MALE);
    searchDto.setOwnerId(1000001L);
    searchDto.setBornAfter(LocalDate.parse("2018-01-01"));

    var horses = horseDao.getAll(searchDto);
    assertThat(horses).isNotNull();
    assertThat(horses.size()).isEqualTo(1);
    assertThat(horses.get(0).getId()).isEqualTo(1000007L);
  }

  @Test
  @Transactional
  public void updatingHorseShouldSucceed() {
    var name = "name";
    var description = "description";
    var gender = Gender.FEMALE;
    var date = LocalDate.now();

    var horse = new Horse();
    horse.setId(1000001L);
    horse.setName(name);
    horse.setDescription(description);
    horse.setGender(gender);
    horse.setBirthdate(date);

    horseDao.update(horse);
    var updatedHorse = horseDao.getById(1000001L);
    assertThat(updatedHorse).isNotNull();
    assertThat(updatedHorse.getName()).isEqualTo(name);
    assertThat(updatedHorse.getDescription()).isEqualTo(description);
    assertThat(updatedHorse.getGender()).isEqualTo(gender);
    assertThat(updatedHorse.getBirthdate()).isEqualTo(date);
    assertThat(updatedHorse.getOwnerId()).isNull();
    assertThat(updatedHorse.getDamId()).isNull();
    assertThat(updatedHorse.getSireId()).isNull();
  }
}
