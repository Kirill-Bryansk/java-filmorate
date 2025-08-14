package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {

    private Integer id;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный email (должен содержать @)")
    private String email;

    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "^[^\\s]+$", message = "Логин должен быть без пробелов")
    private String login;

    private String name;

    @NotNull(message = "Укажи дату рождения")
    @PastOrPresent(message = "Некорректная дата рождения")
    private LocalDate birthday;

    private final Set<Integer> friends = new HashSet<>();
}
