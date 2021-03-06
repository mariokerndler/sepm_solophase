package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.common.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Gender;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
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
    private final OwnerDao ownerDao;

    public HorseJdbcDao(JdbcTemplate jdbcTemplate, OwnerDao ownerDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.ownerDao = ownerDao;
    }

    @Override
    public List<Horse> getAll(HorseSearchDto searchDto) {
        log.trace("calling getAll() ...");

        if(searchDto != null && !searchDto.isEmpty()) {
            return getAllBySearch(searchDto);
        }

        try {
            return jdbcTemplate.query(SQL_SELECT_ALL, this::mapRow);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new NotFoundException("Could not query all horses");
        }
    }

    @Override
    public Horse getById(Long id) {
        return getById(id, 0);
    }

    @Override
    public Horse getById(Long id, int numberOfGenerations) {
        log.trace("calling getById() ...");

        try {
            var horse = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, BeanPropertyRowMapper.newInstance(Horse.class), id);

            if(horse == null) {
                log.error("Horse with id '" + id + "' not found");
                throw new NotFoundException("Horse with id '" + id + "' not found");
            }

            if(horse.getOwnerId() != null) {
                horse.setOwner(ownerDao.getById(horse.getOwnerId()));
            }

            if(numberOfGenerations > 0) {
                numberOfGenerations--;
                if(horse.getDamId() != null) {
                    horse.setDam(getById(horse.getDamId(), numberOfGenerations));
                }

                if(horse.getSireId() != null) {
                    horse.setSire(getById(horse.getSireId(), numberOfGenerations));
                }
            }


            return horse;
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new NotFoundException("Horse with id '" + id + "' not found");
        }
    }

    @Override
    @Transactional
    public Horse create(Horse horse) {
        log.trace("calling create() ...");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> createPreparedStatement(horse, conn, SQL_INSERT), keyHolder);

        horse.setId(((Number) Objects.requireNonNull(keyHolder.getKeys()).get("id")).longValue());
        return horse;
    }

    @Override
    @Transactional
    public Horse update(Horse horse) {
        log.trace("calling update() ...");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            var ps = createPreparedStatement(horse, conn, SQL_UPDATE);
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

    private PreparedStatement createPreparedStatement(Horse horse, Connection conn, String sqlStatement) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
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

    private List<Horse> getAllBySearch(HorseSearchDto searchDto) {
        String sql = buildSearchSQLString(searchDto);

        try {
            return jdbcTemplate.query(sql, this::mapRow);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new NotFoundException("Could not query all horses");
        }
    }

    private String buildSearchSQLString(HorseSearchDto searchDto) {
        log.trace("calling buildSearchSQLString() ...");

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        sb.append("SELECT DISTINCT * FROM ");
        sb.append(TABLE_NAME);

        if(searchDto.getName() != null && !searchDto.getName().isEmpty()) {
            sb.append(" WHERE ");
            first = false;

            sb.append(" LOWER(name) ILIKE LOWER('%")
                    .append(searchDto.getName())
                    .append("%') ");
        }

        if(searchDto.getDescription() != null && !searchDto.getDescription().isEmpty()) {
            if(first) {
                sb.append(" WHERE ");
                first = false;
            }
            else {
                sb.append(" AND ");
            }

            sb.append(" LOWER(description) ILIKE LOWER('%")
                    .append(searchDto.getDescription())
                    .append("%') ");
        }

        if(searchDto.getBornAfter() != null) {
            if(first) {
                sb.append(" WHERE ");
                first = false;
            }
            else {
                sb.append(" AND ");
            }

            sb.append(" birthdate > '")
                    .append(searchDto.getBornAfter())
                    .append("' ");
        }

        if(searchDto.getOwnerId() != null) {
            if(first) {
                sb.append(" WHERE ");
                first = false;
            }
            else {
                sb.append(" AND ");
            }

            sb.append(" ownerId = '")
                    .append(searchDto.getOwnerId())
                    .append("' ");
        }

        if(searchDto.getGender() != null) {
            if(first) {
                sb.append(" WHERE ");
            }
            else {
                sb.append(" AND ");
            }

            sb.append(" LOWER(gender) = LOWER('")
                    .append(searchDto.getGender())
                    .append("')");
        }

        if(searchDto.getLimit() != null) {
            sb.append(" LIMIT ")
                    .append(searchDto.getLimit());
        }

        return sb.toString();
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

        if(ownerId != null) {
            horse.setOwner(ownerDao.getById(ownerId));
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