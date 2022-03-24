package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.*;
import at.ac.tuwien.sepm.assignment.individual.mapper.OwnerMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
    private static final Logger log = LoggerFactory.getLogger(OwnerServiceImpl.class);
    private final OwnerDao dao;
    private final OwnerMapper mapper;

    public OwnerServiceImpl(OwnerDao dao, OwnerMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }


    @Override
    public List<OwnerDto> getOwners() {
        log.trace("calling getOwner() ...");
        var owners = dao.getAll().stream().map(mapper::entityToDto).toList();
        log.info("Retrieved all owner ({})", owners.size());
        return owners;
    }

    @Override
    public OwnerDto getOwnerById(Long id) {
        log.trace("calling getOwnerById() ...");
        var owner = mapper.entityToDto(dao.getById(id));
        log.info("Retrieved owner with id='{}'", owner.id());
        return owner;
    }

    @Override
    public OwnerDto createOwner(AddOwnerDto addOwnerDto) {
        log.trace("calling createOwner() ...");
        var owner = mapper.dtoToEntity(addOwnerDto);
        owner = dao.create(owner);
        log.info("Created new owner with id='{}'", owner.getId());
        return mapper.entityToDto(owner);
    }
}
