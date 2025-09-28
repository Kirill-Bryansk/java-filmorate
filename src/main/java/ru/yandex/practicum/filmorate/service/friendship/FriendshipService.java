package ru.yandex.practicum.filmorate.service.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.friendship.FriendshipDbStorage;
import ru.yandex.practicum.filmorate.repository.users.UserDbStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FriendshipService implements FriendshipServiceInterface {

    @Autowired
    private FriendshipDbStorage friendshipStorage;
    private final UserDbStorage userStorage;

    public FriendshipService(FriendshipDbStorage friendshipStorage, UserDbStorage userStorage) {
        this.friendshipStorage = friendshipStorage;
        this.userStorage = userStorage;
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        userStorage.validateUsersExist(userId, friendId);
        friendshipStorage.removeFriend(userId, friendId);
    }

    @Override
    public void addFriend(int userId, int friendId) {
        userStorage.validateUsersExist(userId, friendId);
        friendshipStorage.addFriend(userId, friendId);
    }

    @Override
    public Set<User> getFriends(int userId) {
        User user = userStorage.getUserById(userId);
        return user.getFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toSet());
    }

    public List<User> getCommonFriends(int userId1, int userId2) {
        Set<Integer> user1Friends = userStorage.getUserById(userId1).getFriends();
        Set<Integer> user2Friends = userStorage.getUserById(userId2).getFriends();
        List<Integer> commonFriends = user1Friends.stream()
                .filter(user2Friends::contains)
                .toList();

        return commonFriends.stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }
}

