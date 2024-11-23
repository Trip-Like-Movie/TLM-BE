package com.TripLikeMovie.backend.global.error.exception.like;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class AlreadyLikedException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new AlreadyLikedException();

    private AlreadyLikedException() {
        super(ErrorCode.ALREADY_LIKED);
    }

}