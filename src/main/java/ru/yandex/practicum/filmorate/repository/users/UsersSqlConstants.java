package ru.yandex.practicum.filmorate.repository.users;

public class UsersSqlConstants {

    // SQL-запрос для добавления нового пользователя в таблицу users
    protected static final String ADD_USER_SQL = """
INSERT INTO users (login, name, birthday, email)
VALUES (?, ?, ?, ?)
""";

    // SQL-запрос для обновления данных пользователя в таблице users
    protected static final String UPDATE_USER_SQL = """
UPDATE users
SET login = ?, name = ?, birthday = ?, email = ?
WHERE user_id = ?
""";

    // SQL-запрос для получения пользователя по его идентификатору
    protected static final String GET_USER_BY_ID_SQL = "SELECT * FROM users WHERE user_id = ?";

    // SQL-запрос для получения всех пользователей из таблицы users
    protected static final String GET_ALL_USERS_SQL = "SELECT * FROM users";

    // SQL-запрос для удаления пользователя по его идентификатору
    protected static final String DELETE_USER_BY_ID_SQL = "DELETE FROM users WHERE user_id = ?";

    // SQL-запрос для удаления всех записей из таблицы users
    protected static final String DELETE_ALL_USERS_SQL = "DELETE FROM users";

}
