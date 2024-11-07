package com.TripLikeMovie.backend.global.error.exception.auth;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class InvalidTokenException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}
