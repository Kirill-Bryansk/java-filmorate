package ru.yandex.practicum.filmorate.service.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.repository.like.LikeDbStorage;

@Service
public class LikeService implements LikeServiceInterface {

    @Autowired
    private LikeDbStorage likeDbStorage;

    @Override
    public void addLike(int filmId, int userId) {
        likeDbStorage.addLike(filmId, userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        likeDbStorage.removeLike(filmId, userId);
    }
}

