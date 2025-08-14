package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public ArrayList<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void addFriend(int userId, int friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);

        if (userId == friendId) {
            throw new IllegalArgumentException("Пользователь не может добавить сам себя в друзья");
        }

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public Set<User> getFriends(int userId) {
        User user = userStorage.getUserById(userId);
        return user.getFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toSet());
    }

    public void removeFriend(int userId, int friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getCommonFriends(int userId1, int userId2) {
        Set<Integer> user1Friends = userStorage.getUserById(userId1).getFriends();
        Set<Integer> user2Friends = userStorage.getUserById(userId2).getFriends();

        user1Friends.retainAll(user2Friends); // Оставляет в user1Friends только общих друзей

        return user1Friends.stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public void deleteUser(int userId) {
        User user = userStorage.getUserById(userId);
        if (user != null) {
            //для удаления userId из списков друзей всех друзей пользователя
            user.getFriends().stream()
                    .map(userStorage::getUserById)
                    .filter(Objects::nonNull)
                    .forEach(friend -> friend.getFriends().remove(userId));

            // Удаляем пользователя
            userStorage.deleteUser(userId);
        } else {
            throw new NotFoundException("Пользователь с указанным ID не найден");
        }
    }
}
