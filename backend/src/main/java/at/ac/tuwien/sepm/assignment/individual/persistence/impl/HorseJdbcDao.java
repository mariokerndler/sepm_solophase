package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.common.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Gender;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class HorseJdbcDao implements HorseDao {
    private static final Logger log = LoggerFactory.getLogger(HorseJdbcDao.class);

    private static final String TABLE_NAME = "horse";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + "(name, description, birthdate, gender, ownerId, damId, sireId) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET name=?, description=?, birthdate=?, gender=?, ownerId=?, damId=?, sireId=? WHERE id=?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public HorseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Horse> getAll() {
        log.trace("calling getAll() ...");

        try {
            return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new NotFoundException("Could not query all horses");
        }
    }

    @Override
    public Horse getById(Long id) {
        log.trace("calling getById() ...");

        try {
            var horse = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Horse.class), id);

            if(horse == null) {
                log.error("Horse with id '" + id + "' not found");
                throw new NotFoundException("Horse with id '" + id + "' not found");
            }

            if(horse.getDamId() != null) {
                horse.setDam(getById(horse.getDamId()));
            }

            if(horse.getSireId() != null) {
                horse.setSire(getById(horse.getSireId()));
            }

            return horse;
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new NotFoundException("Horse with id '" + id + "' not found");
        }
    }

    @Override
    public Horse create(Horse horse) {
        log.trace("calling create() ...");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, horse.getName());

            if(horse.getDescription() != null) {
                ps.setString(2, horse.getDescription());
            }
            else {
                ps.setNull(2, Types.VARCHAR);
            }

            ps.setDate(3, Date.valueOf(horse.getBirthdate()));
            ps.setString(4, horse.getGender().toString());

            if(horse.getOwnerId() != null) {
                ps.setLong(5, horse.getOwnerId());
            }
            else {
                ps.setNull(5, Types.BIGINT);
            }

            if(horse.getDamId() != null) {
                ps.setLong(6, horse.getDamId());
            }
            else {
                ps.setNull(6, Types.BIGINT);
            }

            if(horse.getSire() != null) {
                ps.setLong(7, horse.getSireId());
            }
            else {
                ps.setNull(7, Types.BIGINT);
            }

            return ps;
        }, keyHolder);

        horse.setId(((Number) Objects.requireNonNull(keyHolder.getKeys()).get("id")).longValue());
        return horse;
    }

    @Override
    public Horse update(Horse horse) {
        log.trace("calling update() ...");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_UPDATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, horse.getName());

            if(horse.getDescription() != null) {
                ps.setString(2, horse.getDescription());
            }
            else {
                ps.setNull(2, Types.VARCHAR);
            }

            ps.setDate(3, Date.valueOf(horse.getBirthdate()));
            ps.setString(4, horse.getGender().toString());

            if(horse.getOwnerId() != null) {
                ps.setLong(5, horse.getOwnerId());
            }
            else {
                ps.setNull(5, Types.BIGINT);
            }

            if(horse.getDamId() != null) {
                ps.setLong(6, horse.getDamId());
            }
            else {
                ps.setNull(6, Types.BIGINT);
            }

            if(horse.getSire() != null) {
                ps.setLong(7, horse.getSireId());
            }
            else {
                ps.setNull(7, Types.BIGINT);
            }

            ps.setLong(8, horse.getId());
            return ps;
        }, keyHolder);

        horse.setId(((Number) Objects.requireNonNull(keyHolder.getKeys()).get("id")).longValue());
        return horse;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.trace("calling deleteHorseById() ...");
        var horse = getById(id);
        if(horse.getGender().equals(Gender.FEMALE)) {
            clearDamReference(id);
        }
        else {
            clearSireReference(id);
        }

        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public Optional<Horse> findById(Long id) {
        log.trace("calling findById() ...");
        if(id == null) {
            return Optional.empty();
        }

        var horse = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Horse.class), id);
        return Optional.ofNullable(horse);
    }

    private void clearDamReference(Long id) {
        log.trace("calling clearDamReference() ...");
        String sql = "UPDATE " + TABLE_NAME + " SET damId = null WHERE damId = " + id;
        jdbcTemplate.update(sql);
    }

    private void clearSireReference(Long id) {
        log.trace("calling clearSireReference() ...");
        String sql = "UPDATE " + TABLE_NAME + " SET sireId = null WHERE sireId = " + id;
        jdbcTemplate.update(sql);
    }

    private Horse mapRow(ResultSet result, int rownum) throws SQLException {
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        horse.setName(result.getString("name"));
        horse.setDescription(result.getString("description"));
        horse.setBirthdate(result.getDate("birthdate").toLocalDate());
        horse.setGender(Gender.valueOf(result.getString("gender")));

        Long ownerId = result.getLong("ownerId");
        if(result.wasNull()) {
            ownerId = null;
        }
        horse.setOwnerId(ownerId);

        Long damId = result.getLong("damId");
        if(result.wasNull()) {
            damId = null;
        }

        Long sirId = result.getLong("sireId");
        if(result.wasNull()) {
            sirId = null;
        }

        if(damId != null) {
            horse.setDam(getById(damId));
        }
        if(sirId != null) {
            horse.setSire(getById(sirId));
        }

        horse.setDamId(damId);
        horse.setSireId(sirId);

        return horse;
    }
}