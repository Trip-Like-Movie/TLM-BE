package com.TripLikeMovie.backend.global.error.exception.member;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class EmailPasswordNotMatchException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new EmailPasswordNotMatchException();

    private EmailPasswordNotMatchException() {
        super(ErrorCode.EMAIL_PASSWORD_NOT_MATCH);
    }

}
