package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {
    private Validator validator;
    @Autowired
    private FilmController filmController;
    private Film film;
    @Autowired
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        film = new Film(1, "The Matrix", "The Matrix is a a 1999 science fiction action film" +
                "directed by the Wachowski brothers", LocalDate.of(2004, 5, 20), 124L);
        filmController.getFilms().clear();
        filmService.deleteFilm(film.getId());
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void shouldAddFilm() {
        Film added = filmController.addFilm(film);
        assertNotNull(added.getId());
        assertEquals("The Matrix", added.getName());
    }

    @Test
    void shouldNotValidateOldReleaseDate() {
        film.setReleaseDate(LocalDate.of(1800, 1, 1));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnAllFilms() {
        filmController.addFilm(film);
        Collection<Film> films = filmController.getFilms();
        assertEquals(1, films.size());
    }

    @Test
    void shouldUpdateExistingFilm() {
        Film added = filmController.addFilm(film);
        added.setName("The Matrix 2");

        Film updated = filmController.updateFilm(added);
        assertEquals("The Matrix 2", updated.getName());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdatingNonExistentFilm() {
        film.setId(999);
        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> filmController.updateFilm(film));
        assertEquals("Фильм с id: 999 отсутствует в  списках добавленных", ex.getMessage());
    }
}