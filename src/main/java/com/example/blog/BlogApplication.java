package com.example.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(BlogApplication.class, args);

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
                var parsed = (LinkedHashMap<String, Object>) yaml.load(reader);
                parsed.put("picturePath", reviewDirectory + "\\" + directory + "\\thumbnail.png");
                reviews.add(createReview(parsed));
            }
        } catch (Exception e) {
            System.out.println("Error parsing the reviews: " + e);
        }

        // generate the html content
        ReviewTemplateProcessor processor = new ReviewTemplateProcessor(applicationContext);
        String processed = processor.processTemplate(reviews);
        try (FileWriter fileWriter = new FileWriter("index.html")) {
            fileWriter.write(processed);
        } catch (Exception e) {
            System.out.println("Error writing the html site: " + e);
        }
    }


    private static Review createReview(LinkedHashMap<String, Object> propertyMap) {
        // parse the default values
        var shopTitle = propertyMap.containsKey("shopTitle") ? propertyMap.get("shopTitle").toString() : "ERROR";
        var dishName = propertyMap.containsKey("dishName") ? propertyMap.get("dishName").toString() : "ERROR";
        var reviewerNames = propertyMap.containsKey("reviewerName")
                ? (ArrayList<String>) propertyMap.get("reviewerName")
                : new ArrayList<String>();
        var city = propertyMap.containsKey("city") ? propertyMap.get("city").toString() : "ERROR";
        var picturePath = propertyMap.containsKey("picturePath") ? propertyMap.get("picturePath").toString() : "ERROR";

        // clamp the scores
        var scoreBroth = propertyMap.containsKey("scoreBroth")
                ? Math.clamp((int) propertyMap.get("scoreBroth"), Review.MIN_SCORE, Review.MAX_SCORE_BROTH)
                : -1;
        var scoreNoodles = propertyMap.containsKey("scoreNoodles")
                ? Math.clamp((int) propertyMap.get("scoreNoodles"), Review.MIN_SCORE, Review.MAX_SCORE_BROTH)
                : -1;
        var scoreToppings = propertyMap.containsKey("scoreToppings")
                ? Math.clamp((int) propertyMap.get("scoreToppings"), Review.MIN_SCORE, Review.MAX_SCORE_TOPPINGS)
                : -1;
        var scoreAtmosphere = propertyMap.containsKey("scoreAtmosphere")
                ? Math.clamp((int) propertyMap.get("scoreAtmosphere"), Review.MIN_SCORE, Review.MAX_SCORE_ATMOSPHERE)
                : -1;

        // calculate the total score
        var totalScore = Review.MAX_TOTAL_SCORE * (float) (scoreBroth + scoreNoodles + scoreToppings + scoreAtmosphere)
                / (Review.MAX_SCORE_BROTH + Review.MAX_SCORE_NOODLES + Review.MAX_SCORE_TOPPINGS
                + Review.MAX_SCORE_ATMOSPHERE);

        // parse the specified links
        var links = new ArrayList<LinkWrapper>();
        if(propertyMap.containsKey("cardLinks")) {
            var linkValues = (ArrayList<LinkedHashMap<String, Object>>) propertyMap.get("cardLinks");
            for (var maps : linkValues) {
                for (var keyPair : maps.entrySet()) {
                    links.add(new LinkWrapper(keyPair.getKey(), keyPair.getValue().toString()));
                }
            }
        }

        return new Review(shopTitle, dishName, reviewerNames, city, scoreBroth, scoreNoodles, scoreToppings,
                scoreAtmosphere, links, totalScore, picturePath);
    }
}
