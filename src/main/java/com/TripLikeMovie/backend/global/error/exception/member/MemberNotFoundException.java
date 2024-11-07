package com.TripLikeMovie.backend.global.error.exception.member;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class MemberNotFoundException extends TripLikeMovieException {

    public static final TripLikeMovieException EXCEPTION = new MemberNotFoundException();

    private MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }

}
