package com.TripLikeMovie.backend.global.error.exception.post;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class PostNotFoundException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new PostNotFoundException();

    private PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}