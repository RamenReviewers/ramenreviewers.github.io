package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReviewYmlParser {

    private final static String REVIEW_FILE_NAME = "review.yaml";
    private final static String THUMBNAIL_FILE_NAME = "thumbnail.png";

    public static Review parseReview(Path reviewDirectory) {
        try {
            // Read the YAML for the review data
            File reviewYamlFile = Paths.get(reviewDirectory.toString(), REVIEW_FILE_NAME).toFile();
            FileReader reader = new FileReader(reviewYamlFile);

            var loaderOptions = new LoaderOptions();
            TagInspector tagInspector = tag -> tag.getClassName().equals(Review.class.getName());
            loaderOptions.setTagInspector(tagInspector);

            var yaml = new Yaml(new Constructor(Review.class, loaderOptions));
            var review = yaml.loadAs(reader, Review.class);
            review.setPicturePath(Paths.get(reviewDirectory.toString(), THUMBNAIL_FILE_NAME).toString());
            return review;

        } catch (Exception e) {
            System.out.println("Error parsing the review in directory " + reviewDirectory + ": " + e);
            return null;
        }
    }
}
