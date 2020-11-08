package com.example.demo;

import com.example.demo.controllers.MainController;
import org.springframework.boot.SpringApplication;
import java.io.File;
;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"com.example.demo", "controllers"})
public class DemoApplication {

    public static void main(String[] args) {
        new File(MainController.uploadDirectory).mkdir();
        SpringApplication.run(DemoApplication.class, args);
    }

}
