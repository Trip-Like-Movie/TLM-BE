package com.TripLikeMovie.backend.global.error.exception.movie;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class NotFoundMovieException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new NotFoundMovieException();

    private NotFoundMovieException() {
        super(ErrorCode.MOVIE_NOT_FOUND);
    }

}
