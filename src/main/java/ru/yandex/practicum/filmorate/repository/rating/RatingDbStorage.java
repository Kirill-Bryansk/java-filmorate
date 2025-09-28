package ru.yandex.practicum.filmorate.repository.rating;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.rating.RatingServiceInterface;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.repository.genre.GenreSqlConstants.GET_GENRES_SQL;
import static ru.yandex.practicum.filmorate.repository.like.LikeSqlConstants.GET_LIKES_SQL;
import static ru.yandex.practicum.filmorate.repository.movie.MovieSqlConstants.GET_POPULAR_FILMS_SQL;
import static ru.yandex.practicum.filmorate.repository.rating.RatingSqlConstants.GET_RATING_ID_BY_ID_SQL;
import static ru.yandex.practicum.filmorate.repository.rating.RatingSqlConstants.GET_RATING_ID_BY_NAME_SQL;

@Component
@RequiredArgsConstructor
public class RatingDbStorage implements RatingServiceInterface {
    private final JdbcTemplate jdbc;
    private final FilmRowMapper mapper;

    @Override
    public List<Film> getPopularFilms(int count) {

        List<Film> films = jdbc.query(GET_POPULAR_FILMS_SQL, mapper, count);

        Map<Integer, Film> filmMap = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));

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

    @Override
    public Integer getRatingIdIfExists(Mpa mpa) {
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
}
