package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Link;
import com.ramenreviewers.blog.model.Review;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewYmlParser {

    private static final String MISSING_PROPERTY_DEFAULT_STRING = "MISSING";

    public static Review parseReview(File reviewDirectory, String directory) {
        try {
            // Read the YAML for the review data
            String yamlPath = reviewDirectory + "\\" + directory + "\\review.yaml";
            FileReader reader = new FileReader(yamlPath);
            Map<String, Object> propertyMap = new Yaml().load(reader);

            propertyMap.put("picturePath", reviewDirectory + "\\" + directory + "\\thumbnail.png");
            return createReview(propertyMap);

        } catch (Exception e) {
            System.out.println("Error parsing the review in directory " + directory + ": " + e);
            return null;
        }
    }

    public static Review createReview(Map<String, Object> propertyMap) {
        var shopTitle = getStringValue(propertyMap, "shopTitle");
        var dishName = getStringValue(propertyMap, "dishName");
        var reviewerNames = getListValue(propertyMap, "reviewerName", new ArrayList<>());
        var city = getStringValue(propertyMap, "city");
        var picturePath = getStringValue(propertyMap, "picturePath");

        var scoreBroth = getIntScoreValue(propertyMap, "scoreBroth", Review.MIN_SCORE, Review.MAX_SCORE_BROTH);
        var scoreNoodles = getIntScoreValue(propertyMap, "scoreNoodles", Review.MIN_SCORE, Review.MAX_SCORE_BROTH);
        var scoreToppings = getIntScoreValue(propertyMap, "scoreToppings", Review.MIN_SCORE, Review.MAX_SCORE_TOPPINGS);
        var scoreAtmosphere = getIntScoreValue(propertyMap, "scoreAtmosphere", Review.MIN_SCORE, Review.MAX_SCORE_ATMOSPHERE);

        var totalScore = Math.round(calculateTotalScore(scoreBroth, scoreNoodles, scoreToppings, scoreAtmosphere));

        var links = getCardLinks(propertyMap);

        return new Review(shopTitle, dishName, reviewerNames, city, scoreBroth, scoreNoodles, scoreToppings,
                scoreAtmosphere, links, totalScore, picturePath);
    }

    private static String getStringValue(Map<String, Object> propertyMap, String key) {
        return propertyMap.containsKey(key) ? propertyMap.get(key).toString() : MISSING_PROPERTY_DEFAULT_STRING;
    }

    private static ArrayList<String> getListValue(Map<String, Object> propertyMap, String key, ArrayList<String> defaultValue) {
        return propertyMap.containsKey(key) ? (ArrayList<String>) propertyMap.get(key) : defaultValue;
    }

    private static int getIntScoreValue(Map<String, Object> propertyMap, String key, int minScore, int maxScore) {
        return propertyMap.containsKey(key) ? Math.clamp((int) propertyMap.get(key), minScore, maxScore) : -1;
    }

    private static float calculateTotalScore(int scoreBroth, int scoreNoodles, int scoreToppings, int scoreAtmosphere) {
        return Review.MAX_TOTAL_SCORE * (float) (scoreBroth + scoreNoodles + scoreToppings + scoreAtmosphere)
                / (Review.MAX_SCORE_BROTH + Review.MAX_SCORE_NOODLES + Review.MAX_SCORE_TOPPINGS
                + Review.MAX_SCORE_ATMOSPHERE);
    }

    private static List<Link> getCardLinks(Map<String, Object> propertyMap) {
        if (!propertyMap.containsKey("cardLinks")){
            new ArrayList<Link>();;
        }

        var linkValues = (ArrayList<Map<String, Object>>) propertyMap.get("cardLinks");
        return linkValues.stream()
                .flatMap(maps -> maps.entrySet().stream())
                .map(keyPair -> new Link(keyPair.getKey(), keyPair.getValue().toString()))
                .toList();

    }
}
