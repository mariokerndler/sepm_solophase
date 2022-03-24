package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.AddUpdateHorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.randomdata.RandomHorseGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class HorseServiceTest {

  @Autowired
  HorseService horseService;

  @MockBean
  HorseDao horseDao;

  @Test
  public void creatingHorseShouldReturnCorrectDto() {
    var dam = RandomHorseGenerator.createRandomHorse();
    var sire = RandomHorseGenerator.createRandomHorse();
    var horse = RandomHorseGenerator.createRandomAddUpdateHorseDto();
    horse.setDamId(dam.getId());
    horse.setSireId(sire.getId());

    when(horseDao.getById(dam.getId())).thenReturn(dam);
    when(horseDao.getById(sire.getId())).thenReturn(sire);
    when(horseDao.create(any())).thenAnswer(method -> method.getArgument(0));

    var horseDto = horseService.createHorse(horse);
    assertDtoMatchesAddUpdateDto(horseDto, horse);
    assertDtoMatchesEntity(horseDto.dam(), dam);
    assertDtoMatchesEntity(horseDto.sire(), sire);
  }

  @Test
  public void updatingHorseShouldReturnCorrectDto() {
    var horse = RandomHorseGenerator.createRandomHorse();
    var newHorse = RandomHorseGenerator.createRandomAddUpdateHorseDto();

    when(horseDao.getById(horse.getId())).thenReturn(horse);
    when(horseDao.update(any())).thenAnswer(method -> method.getArgument(0));

    var updatedHorse = horseService.updateHorse(horse.getId(), newHorse);
    assertDtoMatchesAddUpdateDto(updatedHorse, newHorse);
  }

  @Test
  public void creatingHorseWithNonPersistedParentsShouldFail() {
    var dam = RandomHorseGenerator.createRandomHorse();
    var sire = RandomHorseGenerator.createRandomHorse();
    var horse = RandomHorseGenerator.createRandomAddUpdateHorseDto();
    horse.setDamId(dam.getId());
    horse.setSireId(sire.getId());

    assertThatThrownBy(() -> horseService.createHorse(horse)).isInstanceOf(NullPointerException.class);
  }

  @Test
  public void returnedNumberOfGenerationsShouldBeCorrect() {
    var child = RandomHorseGenerator.createRandomHorse();
    var sire = RandomHorseGenerator.createRandomHorse();
    var sireDam = RandomHorseGenerator.createRandomHorse();
    var sireSire = RandomHorseGenerator.createRandomHorse();

    child.setSire(sire);
    child.setSireId(sire.getId());

    sire.setSire(sireSire);
    sire.setSireId(sireSire.getId());
    sire.setDam(sireDam);
    sire.setDamId(sireDam.getId());

    when(horseDao.getById(child.getId())).thenReturn(child);
    when(horseDao.getById(sire.getId())).thenReturn(sire);
    when(horseDao.getById(sireDam.getId())).thenReturn(sireDam);
    when(horseDao.getById(sireSire.getId())).thenReturn(sireSire);

    var childDto = horseService.getHorseById(child.getId(), 2);
    assertThat(childDto).isNotNull();
    assertThat(childDto.sire()).isNotNull();
    assertThat(childDto.sire().dam()).isNotNull();
    assertThat(childDto.sire().sire()).isNotNull();
    assertDtoMatchesEntity(childDto.sire(), sire);
    assertDtoMatchesEntity(childDto.sire().dam(), sireDam);
    assertDtoMatchesEntity(childDto.sire().sire(), sireSire);
  }

  private void assertDtoMatchesEntity(HorseDto dto, Horse entity) {
    assertThat(dto).isNotNull();
    assertThat(dto.name()).isEqualTo(entity.getName());
    assertThat(dto.description()).isEqualTo(entity.getDescription());
    assertThat(dto.birthdate()).isEqualTo(entity.getBirthdate());
    assertThat(dto.gender()).isEqualTo(entity.getGender());
  }

  private void assertDtoMatchesAddUpdateDto(HorseDto dto, AddUpdateHorseDto addUpdateDto) {
    assertThat(dto).isNotNull();
    assertThat(dto.name()).isEqualTo(addUpdateDto.getName());
    assertThat(dto.description()).isEqualTo(addUpdateDto.getDescription());
    assertThat(dto.birthdate()).isEqualTo(addUpdateDto.getBirthdate());
    assertThat(dto.gender()).isEqualTo(addUpdateDto.getGender());
  }
}
