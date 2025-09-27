package ru.yandex.practicum.filmorate.service.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.movie.FilmStorageInterface;
import ru.yandex.practicum.filmorate.repository.users.UserStorageInterface;

import java.util.*;


@Service
@RequiredArgsConstructor
public class FilmService implements FilmServiceInterface {
    @Autowired
    private final FilmStorageInterface filmStorage;
    @Autowired // создает приватные поля
    private final UserStorageInterface userStorage;

    public ArrayList<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public void addLike(int filmId, int userId) {
        validateFilmAndUser(filmId, userId);
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        validateFilmAndUser(filmId, userId);
        filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }

    private void validateFilmAndUser(int filmId, int userId) {
        filmStorage.getFilmById(filmId);
        userStorage.getUserById(userId);
    }
}
