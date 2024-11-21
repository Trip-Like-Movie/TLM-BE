package com.TripLikeMovie.backend.global.error.exception.image;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class BadFileExtensionException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new BadFileExtensionException();

    private BadFileExtensionException() {
        super(ErrorCode.BAD_FILE_EXTENSION);
    }

}
