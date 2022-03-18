package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.AddUpdateHorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/horses")
public class HorseEndpoint {
    private static final Logger log = LoggerFactory.getLogger(HorseEndpoint.class);

    private static final String ID_PATH_VARIABLE_NAME = "id";

    private final HorseService service;

    public HorseEndpoint(HorseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Stream<HorseDto>> getAllHorses() {
        log.info("A user requested all horses.");
        var horseDtos = service.getHorses().stream();
        return ResponseEntity.ok(horseDtos);
    }

    @GetMapping("/{" + ID_PATH_VARIABLE_NAME + "}")
    public ResponseEntity<HorseDto> getHorseById(@PathVariable(ID_PATH_VARIABLE_NAME) Long id) {
        log.info("A user requested the horse with id '{}.", id);
        var horseDto = service.getHorseById(id);
        return ResponseEntity.ok(horseDto);
    }

    @PostMapping
    public ResponseEntity<HorseDto> createHorse(@RequestBody @Valid AddUpdateHorseDto dto) {
        log.info("A user is trying to create a new horse.");
        var addedHorseDto = service.createHorse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedHorseDto);
    }

    @PutMapping("/{" + ID_PATH_VARIABLE_NAME + "}")
    public ResponseEntity<HorseDto> updateHorse(@PathVariable(ID_PATH_VARIABLE_NAME) Long id, @RequestBody @Valid AddUpdateHorseDto dto) {
        log.info("A user is trying to update the horse with id '{}'.", id);
        var updatedHorse = service.updateHorse(id, dto);
        return ResponseEntity.ok(updatedHorse);
    }

    @DeleteMapping("/{" + ID_PATH_VARIABLE_NAME + "}")
    public ResponseEntity<Void> deleteHorse(@PathVariable(ID_PATH_VARIABLE_NAME) Long id) {
        log.info("A user is trying to delete the horse with id '{}'.", id);
        service.deleteHorseById(id);
        return ResponseEntity.noContent().build();
    }
}
