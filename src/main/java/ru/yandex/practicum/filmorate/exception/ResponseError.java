package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;

@Getter
public class ResponseError {
    private final String error;
    private final String description;

    public ResponseError(String error, String description) {
        this.error = error;
        this.description = description;
    }
}