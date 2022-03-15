package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Gender;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class HorseJdbcDao implements HorseDao {
    private static final Logger log = LoggerFactory.getLogger(HorseJdbcDao.class);

    private static final String TABLE_NAME = "horse";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + "(name, description, birthdate, gender) VALUES(?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

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
            throw new PersistenceException("Could not query all horses", e);
        }
    }

    @Override
    public Horse getById(Long id) {
        log.trace("calling getById() ...");

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Horse.class), id);
        } catch (DataAccessException e) {
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

            return ps;
        }, keyHolder);

        horse.setId(((Number) Objects.requireNonNull(keyHolder.getKeys()).get("id")).longValue());
        return horse;
    }

    private Horse mapRow(ResultSet result, int rownum) throws SQLException {
        Horse horse = new Horse();
        horse.setId(result.getLong("id"));
        horse.setName(result.getString("name"));
        horse.setDescription(result.getString("description"));
        horse.setBirthdate(result.getDate("birthdate").toLocalDate());
        horse.setGender(Gender.valueOf(result.getString("gender")));
        horse.setOwnerId(result.getLong("owner"));
        return horse;
    }
}