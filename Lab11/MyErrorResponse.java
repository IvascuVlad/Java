package com.example.demo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/*raspunsul la exceptia generata*/
public class MyErrorResponse {
    String errorMsg;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    LocalDateTime timestamp;

    public MyErrorResponse( String errorMsg) {
        super();
        this.errorMsg = errorMsg;
    }
    public void setTimestamp(LocalDateTime now) {
        timestamp = now;
        System.out.println(errorMsg);
    }
}
