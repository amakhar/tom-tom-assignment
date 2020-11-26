package com.tomtom.exception;

import com.tomtom.model.ErrorResponse;
import lombok.Data;

@Data
public class AppException extends RuntimeException {

    private  String msg;
    private ErrorResponse errorResponse;

    public AppException(String msg, ErrorResponse errorResponse){
        this.msg = msg;
        this.errorResponse = errorResponse;
    }
}
