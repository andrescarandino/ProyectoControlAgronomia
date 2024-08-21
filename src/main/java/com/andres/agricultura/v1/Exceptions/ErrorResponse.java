package com.andres.agricultura.v1.Exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter @Setter @Data
    public class ErrorResponse {
        private Date timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        public ErrorResponse(int status, String error, String message, String path) {
            this.timestamp = new Date();
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }


}
