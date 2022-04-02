package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.enums.Gender;
import at.ac.tuwien.sepm.assignment.individual.randomdata.RandomHorseGenerator;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class HorseEndpointTest {

  private static final String baseUri = "/horses";

  @Autowired
  private WebApplicationContext webAppContext;
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private HorseService horseService;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
  }

  @Test
  public void getHorseShouldSucceed() throws Exception {
    var horseDto = RandomHorseGenerator.createRandomHorseDto();

    when(horseService.getHorseById(eq(horseDto.id()), any(Integer.class))).thenReturn(horseDto);

    var result = mockMvc.perform(get(baseUri + "/{id}", horseDto.id()).characterEncoding("UTF-8"))
            .andExpect(status().isOk()).andReturn();

    var response = result.getResponse().getContentAsString();
    var serializedObject = objectMapper.readValue(response, HorseDto.class);
    assertThat(serializedObject).isEqualTo(horseDto);
  }

  @Test
  public void createNewHorseWithValidPropertiesShouldSucceed() throws Exception {
    var horseDto = RandomHorseGenerator.createRandomAddUpdateHorseDto();
    var horseDtoJSON = objectMapper.writeValueAsString(horseDto);

    mockMvc.perform(post(baseUri).contentType(MediaType.APPLICATION_JSON).content(horseDtoJSON)).andExpect(status().isCreated());
  }

  @Test
  public void createNewHorseWithInvalidPropertiesShouldFail() throws Exception {
    var horseDto = RandomHorseGenerator.createRandomAddUpdateHorseDto();
    horseDto.setGender(null);
    var horseDtoJSON = objectMapper.writeValueAsString(horseDto);

    mockMvc.perform(post(baseUri).contentType(MediaType.APPLICATION_JSON).content(horseDtoJSON))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void getHorsesShouldBundleSearchQueries() throws Exception {
    var name = "Test";
    var description = "Test";
    var date = LocalDate.now();
    var gender = Gender.FEMALE;
    var limit = 1;
    var ownerId = 0L;

    mockMvc.perform(get(baseUri)
            .queryParam("name", name)
            .queryParam("description", description)
            .queryParam("bornAfter", date.toString())
            .queryParam("gender", gender.toString())
            .queryParam("limit", Integer.toString(limit))
            .queryParam("ownerId", Long.toString(ownerId)));

    verify(horseService).getHorses(argThat(searchDto -> {
      assertThat(searchDto).isNotNull();
      assertThat(searchDto.getName()).isEqualTo(name);
      assertThat(searchDto.getDescription()).isEqualTo(description);
      assertThat(searchDto.getBornAfter()).isEqualTo(date);
      assertThat(searchDto.getGender()).isEqualTo(gender);
      assertThat(searchDto.getLimit()).isEqualTo(limit);
      assertThat(searchDto.getOwnerId()).isEqualTo(ownerId);
      return true;
    }));
  }
}
