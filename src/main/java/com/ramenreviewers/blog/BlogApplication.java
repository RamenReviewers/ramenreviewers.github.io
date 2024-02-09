package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

import static com.ramenreviewers.blog.ReviewYmlParser.parseReview;


public class BlogApplication {

    private static final String REVIEWS_DIRECTORY = "reviews";


    public static void main(String[] args) throws URISyntaxException {

        // get all reviews and parse them
        List<Review> reviews;
        File[] reviewDirectories = new File(
                Objects.requireNonNull(BlogApplication.class.getClassLoader().getResource(REVIEWS_DIRECTORY)).toURI()
        ).listFiles();

        if (reviewDirectories == null) {
            System.err.println("no review directories");
            System.exit(1);
        }

        reviews = Arrays.stream(reviewDirectories)
                .map(directory -> parseReview(Paths.get(String.valueOf(directory))))
                .filter(Objects::nonNull)
                .toList();

        // generate the html content
        String processed = ReviewTemplateProcessor.processTemplate(reviews);

        try (FileWriter fileWriter = new FileWriter("index.html")) {
            fileWriter.write(processed);
        } catch (IOException e) {
            System.err.println("Error writing the html site: " + e);
            System.exit(1);
        }
    }

}
