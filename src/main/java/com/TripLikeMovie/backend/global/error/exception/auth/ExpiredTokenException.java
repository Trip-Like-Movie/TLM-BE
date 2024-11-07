package com.TripLikeMovie.backend.global.error.exception.auth;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class ExpiredTokenException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
