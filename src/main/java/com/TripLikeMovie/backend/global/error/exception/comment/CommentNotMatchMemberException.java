package com.TripLikeMovie.backend.global.error.exception.comment;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class CommentNotMatchMemberException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new CommentNotMatchMemberException();

    private CommentNotMatchMemberException() {
        super(ErrorCode.COMMENT_NOT_MATCH_MEMBER);
    }

}
