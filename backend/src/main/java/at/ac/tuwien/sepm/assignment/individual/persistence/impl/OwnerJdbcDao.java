package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.common.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class OwnerJdbcDao implements OwnerDao {
    private static final Logger log = LoggerFactory.getLogger(OwnerJdbcDao.class);

    private static final String TABLE_NAME = "owner";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + "(firstname, surname, email) VALUES(?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public OwnerJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Owner> getAll() {
        log.trace("calling getAll() ...");

        try {
            return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new NotFoundException("Could not query all owner");
        }
    }

    @Override
    public Owner getById(Long id) {
        log.trace("calling getById() ...");

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Owner.class), id);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new NotFoundException("Owner with id '" + id + "' not found");
        }
    }

    @Override
    @Transactional
    public Owner create(Owner owner) {
        log.trace("calling create() ...");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, owner.getFirstname());
            ps.setString(2, owner.getSurname());

            if(owner.getEmail() != null && !owner.getEmail().isEmpty()) {
                ps.setString(3, owner.getEmail());
            }
            else {
                ps.setNull(3, Types.VARCHAR);
            }

            return ps;
        }, keyHolder);

        owner.setId(((Number) Objects.requireNonNull(keyHolder.getKeys()).get("id")).longValue());
        return owner;
    }

    private Owner mapRow(ResultSet result, int rownum) throws SQLException {
        Owner owner = new Owner();
        owner.setId(result.getLong("id"));
        owner.setFirstname(result.getString("firstname"));
        owner.setSurname(result.getString("surname"));
        owner.setEmail(result.getString("email"));
        return owner;
    }
}
