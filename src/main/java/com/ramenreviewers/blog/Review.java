package com.ramenreviewers.blog;

import java.util.ArrayList;

/** Class containing data for a review */
public record Review(String shopTitle, String dishName, ArrayList<String> reviewerNames, String city, int scoreBroth,
                     int scoreNoodles, int scoreToppings, int scoreAtmosphere, ArrayList<LinkWrapper> links,
                     float totalScore, String picturePath) {


    public static final int MAX_TOTAL_SCORE = 5;
    public static final int MAX_SCORE_BROTH = 5;
    public static final int MAX_SCORE_NOODLES = 5;
    public static final int MAX_SCORE_TOPPINGS = 5;
    public static final int MAX_SCORE_ATMOSPHERE = 3;
    public static final int MIN_SCORE = 0;
}
