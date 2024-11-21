package com.TripLikeMovie.backend.global.error.exception.credential;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class PasswordNotMatchException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new PasswordNotMatchException();

    private PasswordNotMatchException() {
        super(ErrorCode.PASSWORD_NOT_MATCH);
    }
}