package ru.yandex.practicum.filmorate.service.rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.rating.RatingDbStorage;

import java.util.List;

@Service
public class RatingService implements RatingServiceInterface {

    @Autowired
    private RatingDbStorage ratingDbStorage;

    @Override
    public List<Film> getPopularFilms(int count) {
        return ratingDbStorage.getPopularFilms(count);
    }

    @Override
    public Integer getRatingIdIfExists(Mpa mpa) {
        return ratingDbStorage.getRatingIdIfExists(mpa);
    }
}
