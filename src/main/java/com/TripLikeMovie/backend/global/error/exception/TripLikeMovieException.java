package com.TripLikeMovie.backend.global.error.exception;

import com.TripLikeMovie.backend.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TripLikeMovieException extends RuntimeException {

    private ErrorCode errorCode;

}
