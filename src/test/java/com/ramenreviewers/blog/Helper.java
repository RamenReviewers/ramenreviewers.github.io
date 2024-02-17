package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;

import java.nio.file.Paths;
import java.util.List;

public class Helper {

    public static Review getValidReview() {
        Review expectedReview = new Review();
        expectedReview.setId("validReview");
        expectedReview.setShop("TestShop");
        expectedReview.setDish("TestDish");
        expectedReview.setReviewers(List.of("Test1", "Test2"));
        expectedReview.setPrice(15f);
        expectedReview.setScoreBroth(5f);
        expectedReview.setScoreNoodles(3.5f);
        expectedReview.setScoreToppings(4f);
        expectedReview.setScoreAtmosphere(2f);
        expectedReview.setLocation("TestLocation");
        expectedReview.setTags(List.of("TEST_TAG", "ANOTHER_TAG"));
        expectedReview.setDescription("TEST DESCRIPTION");
        expectedReview.setPicturePaths(
                List.of(Paths.get("src", "main", "resources", "reviews", "validReview","thumbnail.jpg").toString(),
                        Paths.get("src", "main", "resources", "reviews", "validReview","thumbnail.png").toString()
                )
        );
        var link = new Review.Link();
        link.setDisplayName("test");
        link.setUrl("https://test.com/");
        expectedReview.setLinks(List.of(link));
        return expectedReview;
    }

    public static Review getValidNoImageReview() {
        Review expectedReview = new Review();
        expectedReview.setId("validReviewNoImage");
        expectedReview.setShop("TestShop");
        expectedReview.setDish("TestDish");
        expectedReview.setReviewers(List.of("Test1", "Test2"));
        expectedReview.setPrice(15f);
        expectedReview.setScoreBroth(5f);
        expectedReview.setScoreNoodles(3.5f);
        expectedReview.setScoreToppings(4f);
        expectedReview.setScoreAtmosphere(2f);
        expectedReview.setLocation("TestLocation");
        expectedReview.setTags(List.of());
        expectedReview.setDescription("");
        var link = new Review.Link();
        link.setDisplayName("test");
        link.setUrl("https://test.com/");
        expectedReview.setLinks(List.of(link));
        return expectedReview;
    }
}
