package ru.yandex.practicum.filmorate.repository.like;

public class LikeSqlConstants {

    // SQL-запрос для получения списка фильмов и пользователей, которым эти фильмы понравились
    public static final String GET_LIKES_SQL = "SELECT movie_id, user_id FROM likes";

    // SQL-запрос для получения списка идентификаторов пользователей, которым нравится определённый фильм
    public static final String GET_USERS_WHO_LIKE_MOVIE_SQL = "SELECT user_id FROM likes WHERE movie_id = ?";

    // SQL-запрос для удаления лайков фильма
    public static final String DELETE_LIKES_BY_MOVIE_ID_SQL = "DELETE FROM likes WHERE movie_id = ?";

    // SQL-запрос для добавления нового лайка в таблицу likes
    public static final String ADD_LIKE_SQL = "INSERT INTO likes (movie_id, user_id) VALUES (?, ?)";

    // SQL-запрос для удаления лайка из таблицы likes по идентификаторам фильма и пользователя
    public static final String REMOVE_LIKE_SQL = "DELETE FROM likes WHERE movie_id = ? AND user_id = ?";
}
