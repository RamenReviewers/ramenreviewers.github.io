package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Link;
import com.ramenreviewers.blog.model.Review;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewYmlParser {

    public static Review parseReview(File reviewDirectory, String directory) {
        try {
            // Read the YAML for the review data
            String yamlPath = reviewDirectory + "\\" + directory + "\\review.yaml";
            FileReader reader = new FileReader(yamlPath);

            var loaderOptions = new LoaderOptions();
            TagInspector tagInspector = tag -> tag.getClassName().equals(Review.class.getName());
            loaderOptions.setTagInspector(tagInspector);
            var yaml = new Yaml(new Constructor(Review.class, loaderOptions));
            var review = yaml.loadAs(reader, Review.class);
            review.setPicturePath(reviewDirectory + File.separator + directory + File.separator + "thumbnail.png");
            return review;

        } catch (Exception e) {
            System.out.println("Error parsing the review in directory " + directory + ": " + e);
            return null;
        }
    }
}
