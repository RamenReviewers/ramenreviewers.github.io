package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.ramenreviewers.blog.ReviewYmlParser.parseReview;


public class BlogApplication {

    private static final String REVIEW_DIRECTORY = "src/main/resources/reviews";


    public static void main(String[] args) {

        // get all reviews and parse them
        List<Review> reviews;

        // get all review directories
        var reviewDirectory = new File(REVIEW_DIRECTORY);
        String[] directories = reviewDirectory.list((current, name) -> new File(current, name).isDirectory());

        if (directories == null) {
            System.err.println("review directories are empty");
            return;
        }

        reviews = Arrays.stream(directories)
                .map(directory -> parseReview(reviewDirectory, directory))
                .filter(Objects::nonNull)
                .toList();

        // generate the html content
        String processed = ReviewTemplateProcessor.processTemplate(reviews);

        try (FileWriter fileWriter = new FileWriter("index.html")) {
            fileWriter.write(processed);
        } catch (IOException e) {
            System.out.println("Error writing the html site: " + e);
        }
    }

}
