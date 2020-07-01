package com.voiceplayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * "Play xyz.mp3 at half-speed"  =>
 * [play, xyz.mp3, 50%] =>
 * find xyz.mp3, add metadata for play rate =>
 * response
 * */
@SpringBootApplication
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
