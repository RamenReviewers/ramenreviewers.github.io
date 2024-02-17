package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

import static com.ramenreviewers.blog.ReviewYmlParser.parseReview;


public class PageGenerator {

    private static final String REVIEWS_DIRECTORY = "reviews";


    public static void main(String[] args) throws URISyntaxException {

        // get all reviews and parse them
        List<Review> reviews;
        File[] reviewDirectories = new File(
                Objects.requireNonNull(PageGenerator.class.getClassLoader().getResource(REVIEWS_DIRECTORY)).toURI()
        ).listFiles();

        if (reviewDirectories == null) {
            throw new RuntimeException("no review directories");
        }

        reviews = Arrays.stream(reviewDirectories)
                .map(directory -> parseReview(Paths.get(String.valueOf(directory))))
                .toList();

        // generate the html content
        String processed = ReviewTemplateProcessor.processTemplate(reviews);

        try (FileWriter fileWriter = new FileWriter("index.html")) {
            fileWriter.write(processed);
        } catch (IOException e) {
            throw new RuntimeException("Error writing the html site: ", e);
        }
    }

}
