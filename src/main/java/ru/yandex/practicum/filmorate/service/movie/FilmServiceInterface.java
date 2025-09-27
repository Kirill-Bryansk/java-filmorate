package ru.yandex.practicum.filmorate.service.movie;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.ArrayList;
import java.util.List;

public interface FilmServiceInterface {

    ArrayList<Film> getAllFilms();

    Film getFilmById(int id);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    void addLike(int filmId, int userId);

    void removeLike(int filmId, int userId);

    List<Film> getPopularFilms(int count);

    void deleteFilm(int id);

    void deleteAllFilms();
}
