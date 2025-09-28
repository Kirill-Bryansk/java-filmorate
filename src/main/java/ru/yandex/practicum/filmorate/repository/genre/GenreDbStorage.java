package ru.yandex.practicum.filmorate.repository.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreServiceInterface;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.repository.genre.GenreSqlConstants.*;
import static ru.yandex.practicum.filmorate.repository.genre.GenreSqlConstants.GET_GENRE_ID_BY_NAME_SQL;
import static ru.yandex.practicum.filmorate.repository.movie.movie_ganre.MovieGenreConstants.INSERT_INTO_MOVIE_GENRE_SQL;

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

    @Override
    public Integer getGenreIdIfExists(Genre genre) {
        if (genre == null) return null;

        try {

            return jdbc.queryForObject(GET_GENRE_ID_SQL, Integer.class, genre.getId());
        } catch (EmptyResultDataAccessException e) {
            try {

                return jdbc.queryForObject(GET_GENRE_ID_BY_NAME_SQL, Integer.class, genre.getName());
            } catch (EmptyResultDataAccessException ex) {
                throw new NotFoundException("Жанр '" + genre.getName() + "' не найден");
            }
        }
    }

    @Override
    public void insertGenres(int movieId, Set<Genre> genres) {
        if (genres == null || genres.isEmpty()) return;

        List<Object[]> batchArgs = genres.stream()
                .sorted(Comparator.comparing(Genre::getId))
                .map(genre -> new Object[]{movieId, getGenreIdIfExists(genre)})
                .collect(Collectors.toList());

        jdbc.batchUpdate(INSERT_INTO_MOVIE_GENRE_SQL, batchArgs);
    }
}

