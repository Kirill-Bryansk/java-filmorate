package ru.yandex.practicum.filmorate.repository.mpa;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

import static ru.yandex.practicum.filmorate.repository.mpa.MpaSqlConstants.GET_ALL_MPA_SQL;
import static ru.yandex.practicum.filmorate.repository.mpa.MpaSqlConstants.GET_MPA_BY_ID_SQL;

@Component
public class MpaDbStorage implements MpaStorageInterface {
    private final JdbcTemplate jdbc;

    public MpaDbStorage(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Mpa> getAllMpa() {
        return jdbc.query(GET_ALL_MPA_SQL, new MpaRowMapper());
    }

    @Override
    public Mpa getMpaById(int id) {
        try {
            return jdbc.queryForObject(GET_MPA_BY_ID_SQL, new MpaRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Рейтинг с ID: " + id + " не найден");
        }
    }
}