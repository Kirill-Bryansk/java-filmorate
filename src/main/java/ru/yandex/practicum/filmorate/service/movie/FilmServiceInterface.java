package ru.yandex.practicum.filmorate.service.movie;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

public interface FilmServiceInterface {

    List<Film> getAllFilms();

    Film getFilmById(int id);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilm(int id);

    void deleteAllFilms();
}
