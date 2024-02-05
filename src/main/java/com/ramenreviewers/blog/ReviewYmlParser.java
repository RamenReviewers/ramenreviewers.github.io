package com.ramenreviewers.blog;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReviewYmlParser {


    public static Review parseReview(File reviewDirectory, String directory) {
        try {
            // Read the YAML for the review data
            String yamlPath = reviewDirectory + "\\" + directory + "\\review.yaml";
            FileReader reader = new FileReader(yamlPath);
            Map<String, Object> parsed = new Yaml().load(reader);
            parsed.put("picturePath", reviewDirectory + "\\" + directory + "\\thumbnail.png");
            return createReview(parsed);

        } catch (Exception e) {
            System.out.println("Error parsing the review in directory " + directory + ": " + e);
            return null;
        }
    }

    public static Review createReview(Map<String, Object> propertyMap) {
        var shopTitle = getStringValue(propertyMap, "shopTitle", "ERROR");
        var dishName = getStringValue(propertyMap, "dishName", "ERROR");
        var reviewerNames = getListValue(propertyMap, "reviewerName", new ArrayList<>());
        var city = getStringValue(propertyMap, "city", "ERROR");
        var picturePath = getStringValue(propertyMap, "picturePath", "ERROR");

        var scoreBroth = getIntScoreValue(propertyMap, "scoreBroth", Review.MIN_SCORE, Review.MAX_SCORE_BROTH);
        var scoreNoodles = getIntScoreValue(propertyMap, "scoreNoodles", Review.MIN_SCORE, Review.MAX_SCORE_BROTH);
        var scoreToppings = getIntScoreValue(propertyMap, "scoreToppings", Review.MIN_SCORE, Review.MAX_SCORE_TOPPINGS);
        var scoreAtmosphere = getIntScoreValue(propertyMap, "scoreAtmosphere", Review.MIN_SCORE, Review.MAX_SCORE_ATMOSPHERE);

        var totalScore = calculateTotalScore(scoreBroth, scoreNoodles, scoreToppings, scoreAtmosphere);

        var links = getLinkWrappers(propertyMap);

        return new Review(shopTitle, dishName, reviewerNames, city, scoreBroth, scoreNoodles, scoreToppings,
                scoreAtmosphere, links, totalScore, picturePath);
    }

    private static String getStringValue(Map<String, Object> propertyMap, String key, String defaultValue) {
        return propertyMap.containsKey(key) ? propertyMap.get(key).toString() : defaultValue;
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

    private static ArrayList<LinkWrapper> getLinkWrappers(Map<String, Object> propertyMap) {
        var links = new ArrayList<LinkWrapper>();
        if (propertyMap.containsKey("cardLinks")) {
            var linkValues = (ArrayList<LinkedHashMap<String, Object>>) propertyMap.get("cardLinks");
            links.addAll(linkValues.stream()
                    .flatMap(maps -> maps.entrySet().stream())
                    .map(keyPair -> new LinkWrapper(keyPair.getKey(), keyPair.getValue().toString()))
                    .toList());
        }
        return links;
    }
}
