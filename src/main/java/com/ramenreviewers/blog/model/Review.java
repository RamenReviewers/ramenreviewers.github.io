package com.ramenreviewers.blog.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
public class Review implements Serializable {

    private static final String MISSING_PROPERTY_DEFAULT_STRING = "MISSING";

    public static final int MAX_TOTAL_SCORE = 5;
    public static final int MAX_SCORE_BROTH = 5;
    public static final int MAX_SCORE_NOODLES = 5;
    public static final int MAX_SCORE_TOPPINGS = 5;
    public static final int MAX_SCORE_ATMOSPHERE = 3;
    public static final int MIN_SCORE = -1;

    private @Setter String shop = MISSING_PROPERTY_DEFAULT_STRING;
    private @Setter String dish = MISSING_PROPERTY_DEFAULT_STRING;
    private @Setter List<String> reviewers;
    private @Setter String location = MISSING_PROPERTY_DEFAULT_STRING;
    private float scoreBroth = -1;
    private float scoreNoodles = -1;
    private float scoreToppings = -1;
    private float scoreAtmosphere = -1;
    private @Setter List<LinkedHashMap<Object, Object>> links;
    private @Setter String picturePath = MISSING_PROPERTY_DEFAULT_STRING;


    public void setScoreBroth(float scoreBroth) {
        this.scoreBroth = Math.clamp(scoreBroth, MIN_SCORE, MAX_SCORE_BROTH);
    }

    public void setScoreNoodles(float scoreNoodles) {
        this.scoreNoodles = Math.clamp(scoreNoodles, MIN_SCORE, MAX_SCORE_NOODLES);
    }

    public void setScoreToppings(float scoreToppings) {
        this.scoreToppings = Math.clamp(scoreToppings, MIN_SCORE, MAX_SCORE_TOPPINGS);
    }

    public void setScoreAtmosphere(float scoreAtmosphere) {
        this.scoreAtmosphere = Math.clamp(scoreAtmosphere, MIN_SCORE, MAX_SCORE_BROTH);;
    }

    public float getTotalScore() {
        return MAX_TOTAL_SCORE * (scoreBroth + scoreNoodles + scoreToppings + scoreAtmosphere)
                / (MAX_SCORE_BROTH + MAX_SCORE_NOODLES + MAX_SCORE_TOPPINGS + MAX_SCORE_ATMOSPHERE);
    }

    public List<Link> getLinksParsed() {
        var parsed = new ArrayList<Link>();
        for(var obj : links) {
            for(var entry : obj.entrySet()) {
                parsed.add(new Link(entry.getKey().toString(), entry.getValue().toString()));
            }
        }
        return parsed;
    }
}
