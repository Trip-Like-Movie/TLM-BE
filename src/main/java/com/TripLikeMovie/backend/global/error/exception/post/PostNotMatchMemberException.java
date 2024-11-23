package com.TripLikeMovie.backend.global.error.exception.post;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class PostNotMatchMemberException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new PostNotMatchMemberException();

    private PostNotMatchMemberException() {
        super(ErrorCode.POST_NOT_MATCH_MEMBER);
    }

}
