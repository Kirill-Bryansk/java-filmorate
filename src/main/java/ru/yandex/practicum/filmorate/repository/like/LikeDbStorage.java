package ru.yandex.practicum.filmorate.repository.like;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.service.like.LikeServiceInterface;

import static ru.yandex.practicum.filmorate.repository.like.LikeSqlConstants.ADD_LIKE_SQL;
import static ru.yandex.practicum.filmorate.repository.like.LikeSqlConstants.REMOVE_LIKE_SQL;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeServiceInterface {
    private final JdbcTemplate jdbc;

    @Override
    public void addLike(int filmId, int userId) {
        jdbc.update(ADD_LIKE_SQL, filmId, userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        jdbc.update(REMOVE_LIKE_SQL, filmId, userId);
    }
}
