package ru.yandex.practicum.filmorate.repository.genre;

public class GenreSqlConstants {

    // SQL-запрос для получения списка жанров, связанных с фильмами
    // Запрос выбирает movie_id, genre_id и name из таблиц movie_genre и genres,
    // соединяя их по genre_id, и упорядочивает результаты по genre_id
    public static final String GET_GENRES_SQL = """
                SELECT mg.movie_id, g.genre_id, g.name
                FROM movie_genre mg
                JOIN genres g ON mg.genre_id = g.genre_id
                ORDER BY g.genre_id
            """;

    // SQL-запрос для получения списка жанров фильма по его идентификатору
    public static final String GET_GENRES_BY_MOVIE_ID_SQL = """
                SELECT g.genre_id, g.name
                FROM movie_genre mg
                JOIN genres g ON mg.genre_id = g.genre_id
                WHERE mg.movie_id = ?
                ORDER BY g.genre_id
            """;

    // SQL-запрос для удаления жанров фильма из таблицы movie_genre по идентификатору фильма
    public static final String DELETE_GENRES_SQL = "DELETE FROM movie_genre WHERE movie_id = ?";

    // SQL-запрос для получения идентификатора жанра из таблицы genres по его идентификатору
    public static final String GET_GENRE_ID_SQL = "SELECT genre_id FROM genres WHERE genre_id = ?";

    // SQL-запрос для получения идентификатора жанра из таблицы genres по его названию
    public static final String GET_GENRE_ID_BY_NAME_SQL = "SELECT genre_id FROM genres WHERE name = ?";

    // SQL-запрос для удаления всех записей из таблицы genres
    public static final String DELETE_ALL_GENRES_SQL = "DELETE FROM genres";

    // SQL-запрос для получения списка всех жанров из таблицы genres, упорядоченных по genre_id
    public static final String GET_ALL_GENRES_SQL = "SELECT genre_id, name FROM genres ORDER BY genre_id";

    // SQL-запрос для получения конкретного жанра по его идентификатору (genre_id) из таблицы genres
    public static final String GET_GENRE_BY_ID_SQL = "SELECT genre_id, name FROM genres WHERE genre_id = ?";

}
