package com.TripLikeMovie.backend.global.error.exception.image;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class NotProfileImageException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new NotProfileImageException();

    private NotProfileImageException() {
        super(ErrorCode.NOT_PROFILE_IMAGE);
    }

}
