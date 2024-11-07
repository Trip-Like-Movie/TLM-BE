package com.TripLikeMovie.backend.global.error.exception.auth;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class RefreshTokenExpiredException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new RefreshTokenExpiredException();

    private RefreshTokenExpiredException() {
        super(ErrorCode.EXPIRED_REFRESH_TOKEN);
    }

}
