package com.example.demo;

import com.example.demo.Gomoku.GameServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DemoApplication.class, args);
        GameServer server = new GameServer ();
        System.out.println("Hello world!");
    }

}
