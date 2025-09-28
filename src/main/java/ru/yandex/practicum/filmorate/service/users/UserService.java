package ru.yandex.practicum.filmorate.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.users.UserDbStorage;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserDbStorage userStorage;

    @Override
    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @Override
    public void deleteUser(int id) {
        userStorage.deleteUser(id);
    }

    @Override
    public void deleteAllUsers() {
        userStorage.deleteAllUsers();
    }

    public void validateUsersExist(int userId, int friendId) {
        userStorage.getUserById(userId);
        userStorage.getUserById(friendId);
    }
}
