package com.TripLikeMovie.backend.global.error.exception.image;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class ProfileImageDeleteException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new ProfileImageDeleteException();

    private ProfileImageDeleteException() {
        super(ErrorCode.PROFILE_DELETE_FAIL);
    }

}
