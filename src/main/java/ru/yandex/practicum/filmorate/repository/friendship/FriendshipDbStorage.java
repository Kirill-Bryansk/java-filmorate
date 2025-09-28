package ru.yandex.practicum.filmorate.repository.friendship;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.friendship.FriendshipServiceInterface;

import java.util.HashSet;
import java.util.Set;

import static ru.yandex.practicum.filmorate.repository.friendship.FriendshipSqlConstants.*;
import static ru.yandex.practicum.filmorate.repository.friendship.FriendshipSqlConstants.INSERT_NEW_FRIENDSHIP_REQUEST_SQL;

@Component
@RequiredArgsConstructor
public class FriendshipDbStorage implements FriendshipServiceInterface {

    private final JdbcTemplate jdbc;

    public void addFriend(int userId, int friendId) {
        try {
            String status = jdbc.queryForObject(CHECK_FRIENDSHIP_STATUS_SQL, String.class, friendId, userId);

            if ("PENDING".equals(status)) {
                jdbc.update(UPDATE_FRIENDSHIP_STATUS_TO_CONFIRMED_SQL, friendId, userId);
                jdbc.update(INSERT_MIRROR_FRIENDSHIP_SQL, userId, friendId);
            }
        } catch (EmptyResultDataAccessException e) {
            jdbc.update(INSERT_NEW_FRIENDSHIP_REQUEST_SQL, userId, friendId);
        }
    }

    public void removeFriend(int userId, int friendId) {
        jdbc.update(DELETE_FRIENDSHIP_SQL, userId, friendId);
        jdbc.update(UPDATE_FRIENDSHIP_STATUS_TO_CONFIRMED_SQL, friendId, userId);
    }

    public Set<User> getFriends(int userId) {
        return new HashSet<>(jdbc.query(GET_FRIENDS_SQL, new UserRowMapper(), userId));
    }
}


