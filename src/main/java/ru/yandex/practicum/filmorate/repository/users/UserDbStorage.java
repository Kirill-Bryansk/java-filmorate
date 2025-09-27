package ru.yandex.practicum.filmorate.repository.users;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static ru.yandex.practicum.filmorate.repository.friendship.FriendshipSqlConstants.*;
import static ru.yandex.practicum.filmorate.repository.users.UsersSqlConstants.*;

@Component
public class UserDbStorage implements UserStorageInterface {

    private final JdbcTemplate jdbc;

    public UserDbStorage(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }


    @Override
    public User addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setDate(3, Date.valueOf(user.getBirthday()));
            ps.setString(4, user.getEmail());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        int updated = jdbc.update(UPDATE_USER_SQL,
                user.getLogin(),
                user.getName(),
                Date.valueOf(user.getBirthday()),
                user.getEmail(),
                user.getId());

        if (updated == 0) {
            throw new NotFoundException("Пользователь с id=" + user.getId() + " не найден");
        }
        return user;
    }

    @Override
    public User getUserById(int id) {
        try {
            User user = jdbc.queryForObject(GET_USER_BY_ID_SQL, new UserRowMapper(), id);

            for (User friend : getFriends(id)) {
                user.getFriends().add(friend.getId());
            }
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Пользователь с id=" + id + " не найден");
        }
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(jdbc.query(GET_ALL_USERS_SQL, new UserRowMapper()));
    }

    @Override
    public void deleteUser(int id) {
        jdbc.update(DELETE_FRIENDSHIP_BY_FOLLOWING_USER_SQL, id);
        jdbc.update(DELETE_FRIENDSHIP_BY_FOLLOWED_USER_SQL, id);
        jdbc.update(DELETE_USER_BY_ID_SQL, id);
    }

    @Override
    public void deleteAllUsers() {
        jdbc.update(DELETE_ALL_FRIENDSHIP_SQL);
        jdbc.update(DELETE_ALL_USERS_SQL);
    }

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

