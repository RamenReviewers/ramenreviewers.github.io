package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;
import java.util.Optional;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ReviewYmlParser {

    private final static String REVIEW_FILE_NAME = "review.yaml";
    private final static String RESOURCE_PATH = "src/main/resources/reviews";
    private final static String PLACEHOLDER_IMAGE = "https://via.assets.so/img.jpg?w=400&h=200&tc=grey&bg=white&t=No%20Review%20Image";

    public static Review parseReview(Path reviewDirectory) {
        try {
            // Read the YAML for the review data
            File reviewYamlFile = Paths.get(reviewDirectory.toString(), REVIEW_FILE_NAME).toFile();
            FileReader reader = new FileReader(reviewYamlFile);

            // init the yaml loader
            var loaderOptions = new LoaderOptions();
            TagInspector tagInspector = tag -> tag.getClassName().equals(Review.class.getName());
            loaderOptions.setTagInspector(tagInspector);

            // create a review object by parsing the yaml
            var yaml = new Yaml(new Constructor(Review.class, loaderOptions));
            var review = yaml.loadAs(reader, Review.class);
            review.setId(reviewDirectory.getFileName().toString());

            // get all the images in the same directory
            var images = loadAllImagesInDirectory(reviewDirectory);
            review.setPicturePaths(images);

            return review;

        } catch (IOException e) {
            throw new RuntimeException("Error parsing the review in directory " + reviewDirectory, e);
        }
    }

    private static List<String> loadAllImagesInDirectory(Path directory) {
        // set up a file filter to filter for files with the specified extensions
        final String[] extensions = {".png", ".jpg", ".jpeg", ".gif", ".webp", ".avif"};
        final FilenameFilter filter = (dir, name) ->
            Arrays.stream(extensions).anyMatch(name::endsWith);

        return Optional.ofNullable(directory.toFile().listFiles(filter))
            .map(files -> Arrays.stream(files)
                .map(file -> Paths.get(RESOURCE_PATH, directory.getFileName().toString(),
                    file.getName()).toString())
                .toList())
            .orElse(List.of(PLACEHOLDER_IMAGE));
    }
}
