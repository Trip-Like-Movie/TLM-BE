package com.TripLikeMovie.backend.global.error.exception.movie;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class DuplicatedMovieTitleException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new DuplicatedMovieTitleException();

    private DuplicatedMovieTitleException() {
        super(ErrorCode.DUPLICATED_MOVIE_TITLE);
    }

}
