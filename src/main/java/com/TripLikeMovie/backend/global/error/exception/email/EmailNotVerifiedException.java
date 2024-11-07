package com.TripLikeMovie.backend.global.error.exception.email;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class EmailNotVerifiedException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new EmailNotVerifiedException();

    private EmailNotVerifiedException() {
        super(ErrorCode.EMAIL_NOT_VERIFIED);
    }

}
