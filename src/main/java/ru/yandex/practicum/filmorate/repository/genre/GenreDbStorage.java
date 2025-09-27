package ru.yandex.practicum.filmorate.repository.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreServiceInterface;

import java.util.List;

import static ru.yandex.practicum.filmorate.repository.genre.GenreSqlConstants.GET_ALL_GENRES_SQL;
import static ru.yandex.practicum.filmorate.repository.genre.GenreSqlConstants.GET_GENRE_BY_ID_SQL;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreServiceInterface {

    private final JdbcTemplate jdbc;
    private final GenreMapper genreMapper;

    @Override
    public List<Genre> getAllGenres() {
        return jdbc.query(GET_ALL_GENRES_SQL, genreMapper::mapRowToGenre);
    }

    @Override
    public Genre getGenreById(int id) {
        try {
            return jdbc.queryForObject(GET_GENRE_BY_ID_SQL, genreMapper::mapRowToGenre, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанр с ID: " + id + " не найден");
        }
    }
}

