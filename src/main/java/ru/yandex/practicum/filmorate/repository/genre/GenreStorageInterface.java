package ru.yandex.practicum.filmorate.repository.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorageInterface {
    List<Genre> getAllGenres();

    Genre getGenreById(int id);
}
