package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.ramenreviewers.blog.ReviewYmlParser.parseReview;


public class BlogApplication {

    private static final String REVIEWS_DIRECTORY = "src/main/resources/reviews";


    public static void main(String[] args) {

        // get all reviews and parse them
        List<Review> reviews;

        // get all review directories
        var reviewsDirectory = new File(REVIEWS_DIRECTORY);
        String[] reviewDirectories = reviewsDirectory.list((current, name) -> new File(current, name).isDirectory());

        if (reviewDirectories == null) {
            System.err.println("no review directories");
            return;
        }

        reviews = Arrays.stream(reviewDirectories)
                .map(directory -> parseReview(reviewsDirectory, directory))
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
