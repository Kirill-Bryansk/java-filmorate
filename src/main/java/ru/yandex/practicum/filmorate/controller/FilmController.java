package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
@Validated
public class FilmController {
    private long filmId = 0;
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(++filmId);
        films.put(film.getId(), film);
        log.debug("Фильм {} добавлен", film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.debug("Фильм {} обновлен", film.getName());
            return film;
        }

        log.debug("Фильм с ID {} не найден", film.getId());
        throw new NotFoundException("Фильм с ID:" + film.getId() + " не найден");
    }
}