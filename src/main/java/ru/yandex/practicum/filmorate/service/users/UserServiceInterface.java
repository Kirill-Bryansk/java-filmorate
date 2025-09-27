package ru.yandex.practicum.filmorate.service.users;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserServiceInterface {
    User addUser(User user);

    User updateUser(User user);

    User getUserById(int id);

    ArrayList<User> getAllUsers();

    void deleteUser(int id);

    void deleteAllUsers();

    void validateUsersExist(int userId, int friendId);
}