package ru.yandex.practicum.filmorate.service.like;

public interface LikeServiceInterface {
    void addLike(int filmId, int userId);
    void removeLike(int filmId, int userId);
}
