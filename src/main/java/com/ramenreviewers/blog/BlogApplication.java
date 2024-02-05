package com.ramenreviewers.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class BlogApplication {

    public static void main(String[] args) {

        // get all reviews and pars them
        var reviews = new ArrayList<Review>();
        try {
            // get all review directories that contain a review
            var reviewDirectory = new File("src/main/resources/reviews");
            String[] directories = reviewDirectory.list((current, name) -> new File(current, name).isDirectory());

            // find yamls inside the directories and parse them
            var yaml = new Yaml();
            assert directories != null;
            for (var directory : directories) {
                // read the yaml for the review data
                var yamlPath = reviewDirectory + "\\" + directory + "\\review.yaml";
                var reader = new FileReader(yamlPath);
                var parsed = yaml.load(reader);
                reviews.add(createReview((LinkedHashMap<String, Object>) parsed));
            }
        } catch (Exception e) {
            System.out.println("Error parsing the reviews: " + e);
        }

        // generate the html content


        String processed = ReviewTemplateProcessor.processTemplate(reviews);


        try (FileWriter fileWriter = new FileWriter("index.html")) {
            fileWriter.write(processed);
        } catch (Exception e) {
            System.out.println("Error writing the html site: " + e);
        }
    }


    private static Review createReview(LinkedHashMap<String, Object> propertyMap) {
        // parse the default values
        var shopTitle = propertyMap.get("shopTitle").toString();
        var dishName = propertyMap.get("dishName").toString();
        var reviewerNames = (ArrayList<String>) propertyMap.get("reviewerName");
        var city = propertyMap.get("city").toString();

        // clamp the scores
        var scoreBroth = Math.clamp((int) propertyMap.get("scoreBroth"), Review.MIN_SCORE,
                Review.MAX_SCORE_BROTH);
        var scoreNoodles = Math.clamp((int) propertyMap.get("scoreNoodles"), Review.MIN_SCORE,
                Review.MAX_SCORE_BROTH);
        var scoreToppings = Math.clamp((int) propertyMap.get("scoreToppings"), Review.MIN_SCORE,
                Review.MAX_SCORE_TOPPINGS);
        var scoreAtmosphere = Math.clamp((int) propertyMap.get("scoreAtmosphere"), Review.MIN_SCORE,
                Review.MAX_SCORE_ATMOSPHERE);

        // calculate the total score
        var totalScore = Review.MAX_TOTAL_SCORE * (float) (scoreBroth + scoreNoodles + scoreToppings + scoreAtmosphere)
                / (Review.MAX_SCORE_BROTH + Review.MAX_SCORE_NOODLES + Review.MAX_SCORE_TOPPINGS
                + Review.MAX_SCORE_ATMOSPHERE);

        // parse the specified links
        var links = new ArrayList<LinkWrapper>();
        var linkValues = (ArrayList<LinkedHashMap<String, Object>>) propertyMap.get("cardLinks");
        for (var maps : linkValues) {
            for (var keyPair : maps.entrySet()) {
                links.add(new LinkWrapper(keyPair.getKey(), keyPair.getValue().toString()));
            }
        }

        return new Review(shopTitle, dishName, reviewerNames, city, scoreBroth, scoreNoodles, scoreToppings,
                scoreAtmosphere, links, totalScore);
    }
}
