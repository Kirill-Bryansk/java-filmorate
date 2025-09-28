package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreServiceInterface {

    List<Genre> getAllGenres();

    Genre getGenreById(int id);

    Integer getGenreIdIfExists(Genre genre);

    void insertGenres(int movieId, Set<Genre> genres);
}
