package ru.yandex.practicum.filmorate.repository.rating;

public class RatingSqlConstants {

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

    // SQL-запрос для получения идентификатора рейтинга по его идентификатору
    public static final String GET_RATING_ID_BY_ID_SQL = "SELECT rating_id FROM rating WHERE rating_id = ?";

    // SQL-запрос для получения идентификатора рейтинга по его названию
    public static final String GET_RATING_ID_BY_NAME_SQL = "SELECT rating_id FROM rating WHERE name = ?";
}
