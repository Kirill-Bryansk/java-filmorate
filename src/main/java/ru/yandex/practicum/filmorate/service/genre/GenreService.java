package ru.yandex.practicum.filmorate.service.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

@Service
public class GenreService implements GenreServiceInterface {

    @Autowired
    private final GenreServiceInterface genreStorage;

    public GenreService(GenreServiceInterface genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }

    public Genre getGenreById(int id) {
        return genreStorage.getGenreById(id);
    }

    @Override
    public Integer getGenreIdIfExists(Genre genre) {
        return genreStorage.getGenreIdIfExists(genre);
    }

    @Override
    public void insertGenres(int movieId, Set<Genre> genres) {
        genreStorage.insertGenres(movieId, genres);
    }
}
