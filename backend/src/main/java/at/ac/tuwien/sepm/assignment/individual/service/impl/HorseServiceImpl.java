package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.AddUpdateHorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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
        var horses = dao.getAll().stream().map(horse -> mapper.entityToDto(horse, 0)).toList();
        log.info("Retrieved all horses ({})", horses.size());
        return horses;
    }

    @Override
    public HorseDto getHorseById(Long id, int numberOfGeneration) {
        log.trace("calling getHorseById() ...");
        var horse = dao.getById(id);
        log.info("Retrieved horse with id='{}'", horse.getId());
        return mapper.entityToDto(horse, numberOfGeneration);
    }

    @Override
    public HorseDto createHorse(AddUpdateHorseDto dto) {
        log.trace("calling createHorse() ...");
        var horse = mapper.dtoToEntity(dto);
        setDam(horse, dto.getDamId());
        setSire(horse, dto.getSireId());

        horse = dao.create(horse);
        log.info("Created new horse with id='{}'", horse.getId());
        return mapper.entityToDto(horse, 1);
    }

    @Override
    public HorseDto updateHorse(Long id, AddUpdateHorseDto dto) {
        log.trace("calling updateHorse() ...");

        var horse = dao.getById(id);
        mapper.updateFromDto(horse, dto);
        setDam(horse, dto.getDamId());
        setSire(horse, dto.getSireId());

        horse = dao.update(horse);
        log.info("Updated horse with id '{}'", horse.getId());
        return mapper.entityToDto(horse, 1);
    }

    @Override
    public void deleteHorseById(Long id) {
        log.trace("calling deleteHorseById() ...");
        dao.deleteById(id);
        log.info("Deleted horse with id '{}'", id);
    }

    private void setDam(Horse horse, Long id) {
        setEntityById(id, dao::getById, horse::setDam);
    }

    private void setSire(Horse horse, Long id) {
        setEntityById(id, dao::getById, horse::setSire);
    }

    private <T> void setEntityById(Long id, Function<Long, T> getter, Consumer<T> setter) {
        var entity = (id != null && id != 0) ? getter.apply(id) : null;
        setter.accept(entity);
    }
}
