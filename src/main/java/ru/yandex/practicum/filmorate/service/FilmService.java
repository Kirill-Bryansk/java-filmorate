package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (film == null) {
            throw new NotFoundException("Фильм с таким id не найден: " + filmId);
        }
        if (user == null) {
            throw new NotFoundException("Пользователь с таким id не найден: " + userId);
        }
        film.getLikes().add(userId);
    }

    public void removeLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (film == null) {
            throw new NotFoundException("Фильм с таким id не найден: " + filmId);
        }
        if (user == null) {
            throw new NotFoundException("Пользователь с таким id не найден: " + userId);
        }
        film.getLikes().remove(userId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted(Comparator.comparingInt(film -> -film.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    public void deleteFilm(int id) {
        filmStorage.deleteFilm(id);
    }
}
