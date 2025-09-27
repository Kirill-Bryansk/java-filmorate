package ru.yandex.practicum.filmorate.repository.friendship;

public class FriendshipSqlConstants {

    // SQL-запрос для удаления записей о дружбе, где текущий пользователь является following_user
    public static final String DELETE_FRIENDSHIP_BY_FOLLOWING_USER_SQL = "DELETE FROM friendship WHERE following_user_id = ?";

    // SQL-запрос для удаления записей о дружбе, где текущий пользователь является followed_user
    public static final String DELETE_FRIENDSHIP_BY_FOLLOWED_USER_SQL = "DELETE FROM friendship WHERE followed_user_id = ?";

    // SQL-запрос для удаления всех записей из таблицы friendship
    public static final String DELETE_ALL_FRIENDSHIP_SQL = "DELETE FROM friendship";

    // SQL-запрос для проверки статуса дружбы между пользователями
    public static final String CHECK_FRIENDSHIP_STATUS_SQL = """
            SELECT status FROM friendship
            WHERE following_user_id = ? AND followed_user_id = ?
            """;

    // SQL-запрос для обновления статуса дружбы на 'CONFIRMED'
    public static final String UPDATE_FRIENDSHIP_STATUS_TO_CONFIRMED_SQL = """
            UPDATE friendship
            SET status = 'CONFIRMED'
            WHERE following_user_id = ? AND followed_user_id = ?
            """;

    // SQL-запрос для добавления зеркальной записи о дружбе со статусом 'CONFIRMED'
    public static final String INSERT_MIRROR_FRIENDSHIP_SQL = """
            INSERT INTO friendship (following_user_id, followed_user_id, status)
            VALUES (?, ?, 'CONFIRMED')
            """;

    // SQL-запрос для добавления новой записи о дружбе со статусом 'PENDING'
    public static final String INSERT_NEW_FRIENDSHIP_REQUEST_SQL = """
            INSERT INTO friendship (following_user_id, followed_user_id, status)
            VALUES (?, ?, 'PENDING')
            """;

    // SQL-запрос для удаления записи о дружбе между пользователями
    public static final String DELETE_FRIENDSHIP_SQL = "DELETE FROM friendship WHERE following_user_id = ? AND followed_user_id = ?";

    // SQL-запрос для получения списка друзей пользователя
    public static final String GET_FRIENDS_SQL = """
            SELECT f.followed_user_id AS user_id,
                   u.NAME,
                   u.LOGIN,
                   u.EMAIL,
                   u.BIRTHDAY
            FROM friendship f
            LEFT JOIN users u on u.user_id = f.followed_user_id
            WHERE f.following_user_id = ?
            """;
}
