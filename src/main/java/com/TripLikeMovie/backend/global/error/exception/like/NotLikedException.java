package com.TripLikeMovie.backend.global.error.exception.like;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class NotLikedException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new NotLikedException();

    private NotLikedException() {
        super(ErrorCode.NOT_LIKE);
    }

}
