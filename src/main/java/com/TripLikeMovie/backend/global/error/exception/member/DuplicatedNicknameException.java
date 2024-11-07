package com.TripLikeMovie.backend.global.error.exception.member;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import com.TripLikeMovie.backend.global.error.exception.TripLikeMovieException;

public class DuplicatedNicknameException extends TripLikeMovieException {
    public static final TripLikeMovieException EXCEPTION = new DuplicatedNicknameException();

    private DuplicatedNicknameException() {
        super(ErrorCode.DUPLICATED_NICKNAME);
    }
}
