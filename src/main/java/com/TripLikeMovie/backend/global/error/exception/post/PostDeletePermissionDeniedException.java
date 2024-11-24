package com.TripLikeMovie.backend.global.error.exception.post;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class PostDeletePermissionDeniedException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new PostDeletePermissionDeniedException();

    private PostDeletePermissionDeniedException() {
        super(ErrorCode.POST_DELETE_PERMISSION_DENIED);
    }

}
