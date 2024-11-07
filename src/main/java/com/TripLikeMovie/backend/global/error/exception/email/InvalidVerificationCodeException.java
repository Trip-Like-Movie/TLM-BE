package com.TripLikeMovie.backend.global.error.exception.email;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class InvalidVerificationCodeException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new InvalidVerificationCodeException();

    private InvalidVerificationCodeException() {
        super(ErrorCode.INVALID_VERIFICATION_CODE);
    }
}
