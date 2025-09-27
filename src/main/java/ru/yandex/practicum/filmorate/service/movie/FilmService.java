package ru.yandex.practicum.filmorate.service.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.like.LikeDbStorage;
import ru.yandex.practicum.filmorate.service.users.UserServiceInterface;

import java.util.*;


@Service
public class FilmService implements FilmServiceInterface {
    @Autowired
    private final FilmServiceInterface filmStorage;

    private final LikeDbStorage likeDbStorage;

    @Autowired
    @Qualifier("userDbStorage")
    private final UserServiceInterface userStorage;

    public FilmService(FilmServiceInterface filmStorage, LikeDbStorage likeDbStorage, UserServiceInterface userStorage) {
        this.filmStorage = filmStorage;
        this.likeDbStorage = likeDbStorage;
        this.userStorage = userStorage;
    }
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
        likeDbStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {
        validateFilmAndUser(filmId, userId);
        likeDbStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }

    @Override
    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
    }

    @Override
    public void deleteAllFilms() {
        filmStorage.deleteAllFilms();
    }

    private void validateFilmAndUser(int filmId, int userId) {
        filmStorage.getFilmById(filmId);
        userStorage.getUserById(userId);
    }
}
