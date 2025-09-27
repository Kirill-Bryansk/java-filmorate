package ru.yandex.practicum.filmorate.repository.mpa;

public class MpaSqlConstants {
    // SQL-запрос для получения всех рейтингов из таблицы rating
    protected static final String GET_ALL_MPA_SQL = "SELECT rating_id, name FROM rating";

    // SQL-запрос для получения рейтинга по его идентификатору
    protected static final String GET_MPA_BY_ID_SQL = "SELECT rating_id, name FROM rating WHERE rating_id = ?";
}
