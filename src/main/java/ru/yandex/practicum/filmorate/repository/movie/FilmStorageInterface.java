package ru.yandex.practicum.filmorate.repository.movie;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

public interface FilmStorageInterface {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(int id);

    ArrayList<Film> getAllFilms();

    void deleteFilm(int id);

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    List<Film> getPopularFilms(int count);

    void deleteAllFilms();
}