package com.ahmed.move.error.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AbstractMovieException extends RuntimeException {
    public AbstractMovieException(String message) {
        super(message);
    }

}
