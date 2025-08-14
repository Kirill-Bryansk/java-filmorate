package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private Set<Integer> availableIds = new HashSet<>();

    @Override
    public Film addFilm(Film film) {
        film.setId(idCreator());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        }
        throw new NotFoundException("Фильм с id: " + film.getId() + " отсутствует в  списках добавленных");
    }

    @Override
    public Film getFilmById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new NotFoundException("Фильм с id: " + id + " отсутствует в  списках добавленных");
        }
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void deleteFilm(int id) {
        films.remove(id);
        availableIds.add(id);
    }

    private int idCreator() {
        if (availableIds.isEmpty()) {
            // Если доступных ID нет, генерируем новый уникальный ID
            return films.keySet().stream()
                    .mapToInt(id -> id)
                    .max()
                    .orElse(0) + 1;
        } else {
            // Иначе берём первый доступный ID из набора
            Integer firstAvailableId = availableIds.iterator().next();
            availableIds.remove(firstAvailableId);
            return firstAvailableId;
        }
    }
}
