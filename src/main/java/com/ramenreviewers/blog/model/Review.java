package com.ramenreviewers.blog.model;

import java.beans.JavaBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@JavaBean
public class Review implements Serializable {

    private static final String MISSING_PROPERTY_DEFAULT_STRING = "MISSING";

    public static final int MAX_TOTAL_SCORE = 5;
    public static final int MAX_SCORE_BROTH = 5;
    public static final int MAX_SCORE_NOODLES = 5;
    public static final int MAX_SCORE_TOPPINGS = 5;
    public static final int MAX_SCORE_ATMOSPHERE = 3;
    public static final int MIN_SCORE = -1;

    private String shop = MISSING_PROPERTY_DEFAULT_STRING;
    private String dish = MISSING_PROPERTY_DEFAULT_STRING;
    private List<String> reviewers;
    private String location = MISSING_PROPERTY_DEFAULT_STRING;
    private float scoreBroth = -1;
    private float scoreNoodles = -1;
    private float scoreToppings = -1;
    private float scoreAtmosphere = -1;
    private List<LinkedHashMap<Object, Object>> links;
    private String picturePath = MISSING_PROPERTY_DEFAULT_STRING;

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public List<String> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<String> reviewers) {
        this.reviewers = reviewers;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getScoreBroth() {
        return scoreBroth;
    }

    public void setScoreBroth(float scoreBroth) {
        this.scoreBroth = Math.clamp(scoreBroth, MIN_SCORE, MAX_SCORE_BROTH);
    }

    public float getScoreNoodles() {
        return scoreNoodles;
    }

    public void setScoreNoodles(float scoreNoodles) {
        this.scoreNoodles = Math.clamp(scoreNoodles, MIN_SCORE, MAX_SCORE_NOODLES);
    }

    public float getScoreToppings() {
        return scoreToppings;
    }

    public void setScoreToppings(float scoreToppings) {
        this.scoreToppings = Math.clamp(scoreToppings, MIN_SCORE, MAX_SCORE_TOPPINGS);
    }

    public float getScoreAtmosphere() {
        return scoreAtmosphere;
    }

    public void setScoreAtmosphere(float scoreAtmosphere) {
        this.scoreAtmosphere = Math.clamp(scoreAtmosphere, MIN_SCORE, MAX_SCORE_BROTH);;
    }

    public List<LinkedHashMap<Object, Object>> getLinks() {
        return links;
    }

    public void setLinks(List<LinkedHashMap<Object, Object>> links) {
        this.links = links;
    }

    public float getTotalScore() {
        return MAX_TOTAL_SCORE * (scoreBroth + scoreNoodles + scoreToppings + scoreAtmosphere)
                / (MAX_SCORE_BROTH + MAX_SCORE_NOODLES + MAX_SCORE_TOPPINGS + MAX_SCORE_ATMOSPHERE);
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
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
