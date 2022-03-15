package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.randomdata.RandomHorseGenerator;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "datagen"}) // enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class HorseEndpointTest {

  @Autowired
  private WebApplicationContext webAppContext;
  private MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  private HorseService horseService;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
  }

  @Test
  public void gettingAllHorses() throws Exception {
    byte[] body = mockMvc
        .perform(MockMvcRequestBuilders
            .get("/horses")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andReturn().getResponse().getContentAsByteArray();

    List<HorseDto> horseResult = objectMapper.readerFor(HorseDto.class).<HorseDto>readValues(body).readAll();

    assertThat(horseResult).isNotNull();
    assertThat(horseResult.size()).isEqualTo(0);

    //assertThat(horseResult.get(0).id()).isEqualTo(-3);
    //assertThat(horseResult.get(0).name()).isEqualTo("Lilly");

    //assertThat(horseResult.get(1).id()).isEqualTo(-2);
    //assertThat(horseResult.get(1).name()).isEqualTo("Alex");

    //assertThat(horseResult.get(2).id()).isEqualTo(-1);
    //assertThat(horseResult.get(2).name()).isEqualTo("Bella");
  }

  @Test
  public void getHorseShouldSucceed() throws Exception {
    var horseDto = RandomHorseGenerator.createRandomHorseDto();

    when(horseService.getHorseById(eq(horseDto.id()))).thenReturn(horseDto);

    mockMvc.perform(MockMvcRequestBuilders.get("/horses/{id}", horseDto.id()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }

  @Test
  public void addHorseWithValidParametersShouldSucceed() throws Exception {
    var horseDto = RandomHorseGenerator.createRandomHorseDto();
    var horseDtoJSON = objectMapper.writeValueAsString(horseDto);

    mockMvc.perform(MockMvcRequestBuilders.post("/horses").contentType(MediaType.APPLICATION_JSON).content(horseDtoJSON))
            .andExpect(status().isCreated());
  }

  @Test
  public void gettingNonexistentUrlReturns404() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/asdf123")
        ).andExpect(status().isNotFound());
  }
}
