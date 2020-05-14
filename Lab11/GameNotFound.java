package com.example.demo;

/*exceptia custom*/
public class GameNotFound extends Throwable {
    public GameNotFound(String message, Throwable cause) {
        super(message, cause);
    }

}
