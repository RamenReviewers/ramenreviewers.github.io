package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;
import java.net.URI;
import java.util.Optional;
import org.pkl.config.java.ConfigEvaluator;
import org.pkl.core.ModuleSource;

import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ReviewParser {

    private final static String REVIEW_FILE_NAME = "review.pkl";
    private final static String RESOURCE_PATH = "src/main/resources/reviews";
    private final static String PLACEHOLDER_IMAGE = "https://via.assets.so/img.jpg?w=400&h=200&tc=grey&bg=white&t=No%20Review%20Image";

    public static Review parseReview(Path reviewDirectory) {

            Review review;
            URI reviewPkl = Paths.get(reviewDirectory.toString(), REVIEW_FILE_NAME).toUri();

            try (var evaluator = ConfigEvaluator.preconfigured()) {
                review = evaluator.evaluate(ModuleSource.uri(reviewPkl)).get("review").as(Review.class);
            }catch (Exception e){
                throw new RuntimeException("Error parsing the review: ", e);
            }
            review.setId(reviewDirectory.getFileName().toString());

            // get all the images in the same directory
            var images = loadAllImagesInDirectory(reviewDirectory);
            review.setPicturePaths(images);

            return review;
    }

    private static List<String> loadAllImagesInDirectory(Path directory) {
        // set up a file filter to filter for files with the specified extensions
        final String[] extensions = {".png", ".jpg", ".jpeg", ".gif", ".webp", ".avif"};
        final FilenameFilter filter = (dir, name) ->
            Arrays.stream(extensions).anyMatch(name::endsWith);

        final var files = directory.toFile().listFiles(filter);

        if ( files == null || files.length == 0) {
            return List.of(PLACEHOLDER_IMAGE);
        }

        return Arrays.stream(files)
                .map(file -> Paths.get(RESOURCE_PATH, directory.getFileName().toString(),
                    file.getName()).toString())
                .toList();

    }
}
