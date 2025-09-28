package ru.yandex.practicum.filmorate.service.rating;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface RatingServiceInterface {

    List<Film> getPopularFilms(int count);

    Integer getRatingIdIfExists(Mpa mpa);

}
