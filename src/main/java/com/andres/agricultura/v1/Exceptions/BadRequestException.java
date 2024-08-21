package com.andres.agricultura.v1.Exceptions;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message){
        super(message);
    }
}
