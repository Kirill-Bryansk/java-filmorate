package ru.yandex.practicum.filmorate.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для преобразования строки результата запроса в объект Mpa.
 */
public class MpaRowMapper implements RowMapper<Mpa> {
    /**
     * Метод для преобразования строки результата запроса в объект Mpa.
     *
     * @param rs объект ResultSet, содержащий строку результата запроса
     * @param rowNum номер строки результата запроса
     * @return объект Mpa, созданный на основе данных из ResultSet
     * @throws SQLException если возникает ошибка при доступе к данным в ResultSet
     */
    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("rating_id"), rs.getString("name"));
    }
}
