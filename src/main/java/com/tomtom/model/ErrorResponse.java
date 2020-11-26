package com.tomtom.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@Builder
public class ErrorResponse{
    String errorMsg;
    HttpStatus status;
    Date timestamp;
}
