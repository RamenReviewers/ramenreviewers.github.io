package com.example.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(BlogApplication.class, args);

        ReviewTemplateProcessor processor = new ReviewTemplateProcessor(applicationContext);
        String processed = processor.processTemplate(null);
        try (FileWriter fileWriter = new FileWriter("output/index.html")) {
            fileWriter.write(processed);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
