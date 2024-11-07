package com.TripLikeMovie.backend.global.error.exception.member;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class DuplicatedEmailException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new DuplicatedEmailException();

    private DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL);
    }
}
