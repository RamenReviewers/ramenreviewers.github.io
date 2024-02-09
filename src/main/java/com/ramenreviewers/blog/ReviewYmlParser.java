package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Link;
import com.ramenreviewers.blog.model.Review;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.*;

public class ReviewYmlParser {

    public static Review parseReview(File reviewDirectory, String directory) {
        try {
            // Read the YAML for the review data
            String parsedDirectoryPath = reviewDirectory + File.separator + directory + File.separator;
            String yamlPath = parsedDirectoryPath + "review.yaml";
            FileReader reader = new FileReader(yamlPath);

            // create a review object by parsing the yaml
            var loaderOptions = new LoaderOptions();
            TagInspector tagInspector = tag -> tag.getClassName().equals(Review.class.getName());
            loaderOptions.setTagInspector(tagInspector);
            var yaml = new Yaml(new Constructor(Review.class, loaderOptions));
            var review = yaml.loadAs(reader, Review.class);

            // get all the images in the same directory
            var images = loadAllImagesInDirectory(parsedDirectoryPath);
            review.setPicturePath(images);

            return review;

        } catch (Exception e) {
            System.out.println("Error parsing the review in directory " + directory + ": " + e);
            return null;
        }
    }

    private static List<String> loadAllImagesInDirectory(String directory) {
        // set up a file filter to filter for files with the specified extensions
        final String[] Extensions = {".png", ".jpg"};
        final FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                for (final String ext : Extensions) {
                    if(name.endsWith(ext)) return true;
                }
                return false;
            }
        };

        // return all the files as list
        var dir = new File(directory);
        if(!dir.isDirectory()) return null;
        return Arrays.stream(Objects.requireNonNull(dir.list(filter)))
                .map(file -> directory + file).toList();
    }
}
