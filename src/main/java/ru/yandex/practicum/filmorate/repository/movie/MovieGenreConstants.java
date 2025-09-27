package ru.yandex.practicum.filmorate.repository.movie;

public class MovieGenreConstants {

    // SQL-запрос для получения списка жанров фильмов с их идентификаторами
    public static final String GET_MOVIES_WITH_GENRES_SQL = """
SELECT mg.movie_id, g.genre_id, g.name
FROM movie_genre mg
JOIN genres g ON mg.genre_id = g.genre_id
ORDER BY g.genre_id
""";

    // SQL-запрос для вставки данных в таблицу movie_genre
    public static final String INSERT_INTO_MOVIE_GENRE_SQL = "INSERT INTO movie_genre (movie_id, genre_id) VALUES (?, ?)";
}
