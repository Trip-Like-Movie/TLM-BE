package com.TripLikeMovie.backend.global.error.exception.comment;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class CommentNotFoundException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new CommentNotFoundException();

    private CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }

}
