package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.AddOwnerDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/owners")
public class OwnerEndpoint {
    private static final Logger log = LoggerFactory.getLogger(OwnerEndpoint.class);

    private final OwnerService service;

    public OwnerEndpoint(OwnerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAllOwner() {
        log.info("A user request all owner.");
        var ownerDtos = service.getOwners();
        return ResponseEntity.ok(ownerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable("id") Long id) {
        log.info("A user requested the owner with id '{}'", id);
        var ownerDto = service.getOwnerById(id);
        return ResponseEntity.ok(ownerDto);
    }

    @PostMapping
    public ResponseEntity<OwnerDto> createOwner(@RequestBody @Valid AddOwnerDto addOwnerDto) {
        log.info("A user is trying to create a new owner.");
        var createdOwnerDto = service.createOwner(addOwnerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOwnerDto);
    }
}
