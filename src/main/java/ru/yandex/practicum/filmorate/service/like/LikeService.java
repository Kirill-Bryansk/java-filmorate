package ru.yandex.practicum.filmorate.service.like;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.repository.like.LikeDbStorage;
import ru.yandex.practicum.filmorate.repository.movie.FilmDbStorage;
import ru.yandex.practicum.filmorate.repository.users.UserDbStorage;

@Service
@RequiredArgsConstructor
public class LikeService implements LikeServiceInterface {

    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;
    private final LikeDbStorage likeDbStorage;

    @Override
    public void addLike(int filmId, int userId) {
        validateFilmAndUser(filmId, userId);
        likeDbStorage.addLike(filmId, userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        validateFilmAndUser(filmId, userId);
        likeDbStorage.removeLike(filmId, userId);
    }

    private void validateFilmAndUser(int filmId, int userId) {
        filmStorage.getFilmById(filmId);
        userStorage.getUserById(userId);
    }
}


