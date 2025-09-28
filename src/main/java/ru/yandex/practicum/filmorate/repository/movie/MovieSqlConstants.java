package ru.yandex.practicum.filmorate.repository.movie;

public class MovieSqlConstants {

    // SQL-запрос для вставки данных о фильме в таблицу movies
    public static final String INSERT_FILM_SQL = "INSERT INTO movies (name, description, release_date, duration, rating_id) VALUES (?, ?, ?, ?, ?)";

    // SQL-запрос для обновления данных фильма в базе данных по его идентификатору
    public static final String UPDATE_FILM_SQL = """
            UPDATE movies
            SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ?
            WHERE movie_id = ?""";

    // SQL-запрос для удаления всех записей из таблицы likes
    public static final String DELETE_ALL_LIKES_SQL = "DELETE FROM likes";

    // SQL-запрос для получения списка популярных фильмов с их рейтингами и количеством лайков
    public static final String GET_POPULAR_FILMS_SQL = """
            SELECT m.movie_id, m.name, m.description, m.release_date, m.duration, r.rating_id, r.name AS rating_name,
                   COUNT(l.user_id) AS likes_count
            FROM movies m
            LEFT JOIN rating r ON m.rating_id = r.rating_id
            LEFT JOIN likes l ON m.movie_id = l.movie_id
            GROUP BY m.movie_id, r.rating_id, r.name
            ORDER BY likes_count DESC
            LIMIT ?
            """;
    // SQL-запрос для проверки существования фильма в базе данных по его идентификатору
    public static final String CHECK_FILM_SQL = "SELECT COUNT(*) FROM movies WHERE movie_id = ?";

    // SQL-запрос для удаления всех записей из таблицы movies
    public static final String DELETE_ALL_MOVIES_SQL = "DELETE FROM movies";

    // SQL-запрос для удаления записи о фильме по его идентификатору
    public static final String DELETE_MOVIE_BY_ID_SQL = "DELETE FROM movies WHERE movie_id = ?";

}
