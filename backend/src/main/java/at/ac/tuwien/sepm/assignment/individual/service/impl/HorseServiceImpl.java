package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {
    private static final Logger log = LoggerFactory.getLogger(HorseServiceImpl.class);
    private final HorseDao dao;
    private final HorseMapper mapper;

    public HorseServiceImpl(HorseDao dao, HorseMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public List<HorseDto> getHorses() {
        log.trace("calling getHorses() ...");
        var horses = dao.getAll().stream().map(mapper::entityToDto).toList();
        log.info("Retrieved all horses ({})", horses.size());
        return horses;
    }

    @Override
    public HorseDto getHorseById(Long id) {
        log.trace("calling getHorseById() ...");
        var horse = dao.getById(id);
        log.info("Retrieved horse with id='{}'", horse.getId());
        return mapper.entityToDto(horse);
    }

    @Override
    public HorseDto createHorse(HorseDto dto) {
        log.trace("calling createHorse() ...");
        var horse = mapper.entityToDto(dao.create(mapper.dtoToEntity(dto)));
        log.info("Created new horse with id='{}'", horse.id());
        return horse;
    }

    @Override
    public HorseDto updateHorse(Long id, HorseDto dto) {
        log.trace("calling updateHorse() ...");

        var horse = dao.getById(id);
        mapper.updateFromDto(horse, dto);

        horse = dao.update(horse);
        log.info("Updated horse with id '{}'", horse.getId());
        return mapper.entityToDto(horse);
    }
}
