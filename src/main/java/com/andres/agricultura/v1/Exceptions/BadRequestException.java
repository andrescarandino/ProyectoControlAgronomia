package com.andres.agricultura.v1.Exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Data
@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message){
        super(message);
    }
}
