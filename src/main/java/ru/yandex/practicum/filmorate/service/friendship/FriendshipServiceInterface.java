package ru.yandex.practicum.filmorate.service.friendship;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface FriendshipServiceInterface {

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    Set<User> getFriends(int userId);
}
