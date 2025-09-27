package ru.yandex.practicum.filmorate.repository.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorageInterface {
    List<Mpa> getAllMpa();

    Mpa getMpaById(int id);
}
