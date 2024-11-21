package com.TripLikeMovie.backend.global.error.exception.image;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class FileEmptyException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new FileEmptyException();

    private FileEmptyException() {
        super(ErrorCode.FILE_EXCEPTION);
    }

}
