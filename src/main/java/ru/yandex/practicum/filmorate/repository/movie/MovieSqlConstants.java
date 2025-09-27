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


/*

    // SQL-запрос для вставки данных о фильме в таблицу movies
    public static final String INSERT_FILM_SQL = "INSERT INTO movies (name, description, release_date, duration, rating_id) VALUES (?, ?, ?, ?, ?)";
    //Запрос для проверки существования фильма в базе данных по его идентификатору.
    public static final String CHECK_FILM_SQL = "SELECT COUNT(*) FROM movies WHERE movie_id = ?";

    // SQL-запрос для обновления данных фильма в базе данных по его идентификатору
    public static final String UPDATE_FILM_SQL = """
            UPDATE movies
            SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ?
            WHERE movie_id = ?""";

    // SQL-запрос для удаления жанров фильма из таблицы movie_genre по идентификатору фильма
    public static final String DELETE_GENRES_SQL = "DELETE FROM movie_genre WHERE movie_id = ?";

    // SQL-запрос для получения подробной информации о фильме по его идентификатору, включая рейтинг
    public static final String GET_FILM_BY_ID_SQL = """
                SELECT
                    m.movie_id,
                    m.name,
                    m.description,
                    m.release_date,
                    m.duration,
                    r.rating_id,
                    r.name AS rating_name
                FROM movies m
                LEFT JOIN rating r ON m.rating_id = r.rating_id
                WHERE m.movie_id = ?
            """;

    // SQL-запрос для получения списка жанров фильма по его идентификатору
    public static final String GET_GENRES_BY_MOVIE_ID_SQL = """
                SELECT g.genre_id, g.name
                FROM movie_genre mg
                JOIN genres g ON mg.genre_id = g.genre_id
                WHERE mg.movie_id = ?
                ORDER BY g.genre_id
            """;

    // SQL-запрос для получения списка идентификаторов пользователей, которым нравится определённый фильм
    public static final String GET_USERS_WHO_LIKE_MOVIE_SQL = "SELECT user_id FROM likes WHERE movie_id = ?";

    // SQL-запрос для получения списка фильмов с их рейтингами
    public static final String GET_MOVIES_WITH_RATINGS_SQL = """
                SELECT
                    m.movie_id,
                    m.name,
                    m.description,
                    m.release_date,
                    m.duration,
                    r.rating_id,
                    r.name AS rating_name
                FROM movies m
                LEFT JOIN rating r ON m.rating_id = r.rating_id
            """;

    // SQL-запрос для получения списка фильмов с их жанрами, упорядоченного по идентификатору жанра
    public static final String GET_MOVIES_WITH_GENRES_SQL = """
                SELECT mg.movie_id, g.genre_id, g.name
                FROM movie_genre mg
                JOIN genres g ON mg.genre_id = g.genre_id
                ORDER BY g.genre_id
            """;

    // SQL-запрос для получения списка пар (movie_id, user_id) из таблицы likes,
    // показывающий какие пользователи лайкнули какие фильмы
    public static final String GET_LIKES_SQL = "SELECT movie_id, user_id FROM likes";

    // SQL-запрос для удаления лайков фильма
    public static final String DELETE_LIKES_BY_MOVIE_ID_SQL = "DELETE FROM likes WHERE movie_id = ?";

    // SQL-запрос для удаления жанров фильма
    public static final String DELETE_GENRES_BY_MOVIE_ID_SQL = "DELETE FROM movie_genre WHERE movie_id = ?";

    // SQL-запрос для удаления фильма
    public static final String DELETE_MOVIE_BY_ID_SQL = "DELETE FROM movies WHERE movie_id = ?";

    // SQL-запрос для удаления всех записей из таблицы likes
    public static final String DELETE_ALL_LIKES_SQL = "DELETE FROM likes";

    // SQL-запрос для удаления всех записей из таблицы movie_genre
    public static final String DELETE_ALL_GENRES_SQL = "DELETE FROM movie_genre";

    // SQL-запрос для удаления всех записей из таблицы movies
    public static final String DELETE_ALL_MOVIES_SQL = "DELETE FROM movies";

    // SQL-запрос для добавления нового лайка в таблицу likes
    public static final String ADD_LIKE_SQL = "INSERT INTO likes (movie_id, user_id) VALUES (?, ?)";

    // SQL-запрос для удаления лайка из таблицы likes по идентификаторам фильма и пользователя
    public static final String REMOVE_LIKE_SQL = "DELETE FROM likes WHERE movie_id = ? AND user_id = ?";

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

    // SQL-запрос для получения идентификатора жанра из таблицы genres по его идентификатору
    public static final String GET_GENRE_ID_SQL = "SELECT genre_id FROM genres WHERE genre_id = ?";

    // SQL-запрос для получения идентификатора жанра из таблицы genres по его названию
    public static final String GET_GENRE_ID_BY_NAME_SQL = "SELECT genre_id FROM genres WHERE name = ?";

    // SQL-запрос для получения идентификатора рейтинга по его идентификатору
    public static final String GET_RATING_ID_BY_ID_SQL = "SELECT rating_id FROM rating WHERE rating_id = ?";

    // SQL-запрос для получения идентификатора рейтинга по его названию
    public static final String GET_RATING_ID_BY_NAME_SQL = "SELECT rating_id FROM rating WHERE name = ?";

    // SQL-запрос для вставки данных в таблицу movie_genre
    public static final String INSERT_INTO_MOVIE_GENRE_SQL = "INSERT INTO movie_genre (movie_id, genre_id) VALUES (?, ?)";
*/

}
