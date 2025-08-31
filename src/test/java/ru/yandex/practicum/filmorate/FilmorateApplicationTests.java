package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class, GenreDbStorage.class, FilmDbStorage.class, MpaDbStorage.class})
@ContextConfiguration(classes = {UserDbStorage.class, GenreDbStorage.class, FilmDbStorage.class, MpaDbStorage.class, FilmorateConfig.class})
class FilmorateApplicationTests {

	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;
	private final GenreDbStorage genreStorage;
	private final MpaDbStorage mpaStorage;

	@BeforeEach
	void setUp() {
		filmStorage.deleteAllFilms();
		userStorage.deleteAllUsers();

		userStorage.addUser(new User(1, "alex@example.com", "alex_gray", "Alex Gray", LocalDate.of(1995, 3, 20)));
		userStorage.addUser(new User(2, "sarah@example.com", "sarah_lee", "Sarah Lee", LocalDate.of(1988, 11, 5)));
		userStorage.addUser(new User(3, "david@example.com", "david_king", "David King", LocalDate.of(2000, 8, 9)));

		Film matrix = new Film(1, "The Matrix", "A science fiction action film about a dystopian future where reality is a simulation.",
				LocalDate.of(1999, 3, 31), 136, new HashSet<>(), mpaStorage.getMpaById(4), new LinkedHashSet<>());

		Film avatar = new Film(2, "Avatar", "A visually stunning science fiction epic set on the alien moon Pandora.",
				LocalDate.of(2009, 12, 18), 162, new HashSet<>(), mpaStorage.getMpaById(4), new LinkedHashSet<>());

		Film Inception = new Film(3, "Inception", "A psychological sci-fi thriller about dream infiltration.",
				LocalDate.of(2010, 7, 16), 148, new HashSet<>(), mpaStorage.getMpaById(4), new LinkedHashSet<>());

		filmStorage.addFilm(matrix);
		filmStorage.addFilm(avatar);
		filmStorage.addFilm(Inception);

		int id = userStorage.getAllUsers().getFirst().getId();
		userStorage.addFriend(id, id + 1);
	}

	@Test
	void testGetAllUsers() {
		List<User> users = userStorage.getAllUsers();

		assertThat(users).hasSize(3);
		assertThat(users).extracting(User::getName)
				.containsExactlyInAnyOrder("Alex Gray", "Sarah Lee", "David King");
	}

	@Test
	public void testGetUserById() {

		int id = userStorage.getAllUsers().getFirst().getId();
		Optional<User> userOptional = Optional.ofNullable(userStorage.getUserById(id));

		assertThat(userOptional)
				.isPresent()
				.hasValueSatisfying(user ->
						assertThat(user).hasFieldOrPropertyWithValue("id", id)
				);
	}

	@Test
	void testGetUserFriends() {
		int id = userStorage.getAllUsers().getFirst().getId();
		Set<User> friends = userStorage.getFriends(id);
		assertThat(friends).hasSize(1);
	}

	@Test
	void testAddUser() {
		User newUser = new User(7, "Ivan@yandex.com", "Ivan_s", "Ivan Ivanov", LocalDate.of(2004, 5, 20));
		userStorage.addUser(newUser);
		int id = userStorage.getAllUsers().getLast().getId();

		User addedUser = userStorage.getUserById(id);

		assertThat(addedUser).isNotNull();
		assertThat(addedUser.getName()).isEqualTo("Ivan Ivanov");
	}

	@Test
	void testGetAllFilms() {
		List<Film> films = filmStorage.getAllFilms();

		assertThat(films).hasSize(3);
		assertThat(films).extracting(Film::getName)
				.containsExactlyInAnyOrder("Inception", "The Notebook", "Interstellar");
	}

	@Test
	void testGetMovieById() {
		Film film = filmStorage.getFilmById(1);

		assertThat(film).isNotNull();
		assertThat(film.getName()).isEqualTo("Inception");
		assertThat(film.getDescription()).isEqualTo("A mind-bending thriller by Christopher Nolan.");
	}


	@Test
	void testGetAllGenres() {
		List<Genre> genres = genreStorage.getAllGenres();

		assertThat(genres).hasSize(6);
		assertThat(genres).extracting(Genre::getName)
				.containsExactlyInAnyOrder("Комедия", "Драма", "Мультфильм", "Триллер", "Документальный", "Боевик");
	}

	@Test
	void testGetGenreById() {
		Genre genre = genreStorage.getGenreById(1);

		assertThat(genre).isNotNull();
		assertThat(genre.getName()).isEqualTo("Комедия");
	}

	@Test
	void testGetAllRatings() {
		List<Mpa> ratings = mpaStorage.getAllMpa();

		assertThat(ratings).hasSize(5);
		assertThat(ratings).extracting(Mpa::getName)
				.containsExactlyInAnyOrder("G", "PG", "PG-13", "R", "NC-17");
	}

	@Test
	void testGetRatingById() {
		Mpa rating = mpaStorage.getMpaById(4);

		assertThat(rating).isNotNull();
		assertThat(rating.getName()).isEqualTo("R");
	}
}