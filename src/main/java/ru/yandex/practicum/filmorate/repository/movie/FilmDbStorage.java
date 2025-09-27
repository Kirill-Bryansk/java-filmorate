package ru.yandex.practicum.filmorate.repository.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.movie.FilmServiceInterface;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.repository.genre.GenreSqlConstants.*;
import static ru.yandex.practicum.filmorate.repository.like.LikeSqlConstants.*;
import static ru.yandex.practicum.filmorate.repository.movie.MovieGenreConstants.*;
import static ru.yandex.practicum.filmorate.repository.movie.MovieSqlConstants.*;
import static ru.yandex.practicum.filmorate.repository.rating.RatingSqlConstants.*;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements FilmServiceInterface {
    private final JdbcTemplate jdbc;
    private final FilmRowMapper mapper;

    @Override
    public Film addFilm(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_FILM_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setLong(4, film.getDuration());
            ps.setObject(5, getRatingIdIfExists(film.getMpa()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new DataAccessException("Не удалось получить id фильма после вставки") {
            };
        }
        int filmId = key.intValue();

        Set<Genre> sortedGenres = film.getGenres().stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        insertGenres(filmId, film.getGenres());
        film.setId(filmId);
        film.setGenres(sortedGenres);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        Integer count = jdbc.queryForObject(CHECK_FILM_SQL, Integer.class, film.getId());
        if (count == null || count == 0) {
            throw new NotFoundException("Фильм с ID: " + film.getId() + " не найден");
        }

        Integer ratingId = getRatingIdIfExists(film.getMpa());

        jdbc.update(UPDATE_FILM_SQL,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                ratingId,
                film.getId()
        );

        jdbc.update(DELETE_GENRES_SQL, film.getId());

        insertGenres(film.getId(), film.getGenres());

        return getFilmById(film.getId());
    }

    @Override
    public Film getFilmById(int id) {
        Film film = jdbc.queryForObject(GET_FILM_BY_ID_SQL, mapper, id);

        jdbc.query(GET_GENRES_BY_MOVIE_ID_SQL, (rs) -> {
            if (film != null) {
                try {
                    Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("name"));
                    film.getGenres().add(genre);
                } catch (IllegalArgumentException e) {
                    throw new NotFoundException("Такого жанра не существует");
                }
            } else {
                throw new NotFoundException("Фильм с ID: " + id + " не найден");
            }
        }, id);

        jdbc.query(GET_USERS_WHO_LIKE_MOVIE_SQL, (rs) -> {
            if (film != null) {
                film.getLikes().add(rs.getInt("user_id"));
            }
        }, id);

        return film;
    }

    @Override
    public ArrayList<Film> getAllFilms() {

        ArrayList<Film> films = new ArrayList<>(jdbc.query(GET_MOVIES_WITH_RATINGS_SQL, mapper));

        Map<Integer, Film> filmMap = films.stream()
                .collect(Collectors.toMap(Film::getId, Function.identity()));


        jdbc.query(GET_MOVIES_WITH_GENRES_SQL, (rs) -> {
            int movieId = rs.getInt("movie_id");
            Film film = filmMap.get(movieId);
            if (film != null) {
                try {
                    Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("name"));
                    film.getGenres().add(genre);
                } catch (IllegalArgumentException e) {
                    throw new NotFoundException("Такого жанра не существует");
                }
            }
        });


        jdbc.query(GET_LIKES_SQL, (rs) -> {
            int movieId = rs.getInt("movie_id");
            Film film = filmMap.get(movieId);
            if (film != null) {
                film.getLikes().add(rs.getInt("user_id"));
            }
        });

        return films;
    }

    @Override
    public void deleteFilm(int id) {
        jdbc.update(DELETE_LIKES_BY_MOVIE_ID_SQL, id);
        jdbc.update(DELETE_GENRES_SQL, id);

        int rowsAffected = jdbc.update(DELETE_MOVIE_BY_ID_SQL, id);

        if (rowsAffected == 0) {
            throw new NotFoundException("Фильм с ID: " + id + " не найден");
        }
    }

    @Override
    public void deleteAllFilms() {
        jdbc.update(DELETE_ALL_LIKES_SQL);
        jdbc.update(DELETE_ALL_GENRES_SQL);
        jdbc.update(DELETE_ALL_MOVIES_SQL);
    }

    @Override
    public List<Film> getPopularFilms(int count) {

        List<Film> films = jdbc.query(GET_POPULAR_FILMS_SQL, mapper, count);

        Map<Integer, Film> filmMap = films.stream()
                .collect(Collectors.toMap(Film::getId, Function.identity()));

        jdbc.query(GET_GENRES_SQL, (rs) -> {
            int movieId = rs.getInt("movie_id");
            Film film = filmMap.get(movieId);
            if (film != null) {
                Genre genre = new Genre(rs.getInt("genre_id"), rs.getString("name"));
                film.getGenres().add(genre);
            }
        });

        jdbc.query(GET_LIKES_SQL, (rs) -> {
            int movieId = rs.getInt("movie_id");
            Film film = filmMap.get(movieId);
            if (film != null) {
                film.getLikes().add(rs.getInt("user_id"));
            }
        });

        return films;
    }

    private Integer getGenreIdIfExists(Genre genre) {
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

    private Integer getRatingIdIfExists(Mpa mpa) {
        if (mpa == null) return null;
        try {
            return jdbc.queryForObject(GET_RATING_ID_BY_ID_SQL, Integer.class, mpa.getId());
        } catch (EmptyResultDataAccessException e) {
            try {
                return jdbc.queryForObject(GET_RATING_ID_BY_NAME_SQL, Integer.class, mpa.getName());
            } catch (EmptyResultDataAccessException ex) {
                throw new NotFoundException("Рейтинг '" + mpa.getName() + "' не найден");
            }
        }
    }

    private void insertGenres(int movieId, Set<Genre> genres) {
        if (genres == null || genres.isEmpty()) return;

        List<Object[]> batchArgs = genres.stream()
                .sorted(Comparator.comparing(Genre::getId))
                .map(genre -> new Object[]{movieId, getGenreIdIfExists(genre)})
                .collect(Collectors.toList());

        jdbc.batchUpdate(INSERT_INTO_MOVIE_GENRE_SQL, batchArgs);
    }
}
