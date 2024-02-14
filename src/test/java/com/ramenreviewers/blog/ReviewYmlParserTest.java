package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ReviewYmlParserTest {

    @Test
    @SneakyThrows
    void parseValidReview() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(BlogApplication.class.getClassLoader().getResource("validReview")).toURI());
        Review result = ReviewYmlParser.parseReview(reviewDirectory);
        Review expectedReview = getValidReview();

        assertEquals(expectedReview, result);
    }

    private static Review getValidReview() {
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

    @Test
    @SneakyThrows
    void parseReviewWithInvalidYAML() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(BlogApplication.class.getClassLoader().getResource("invalidReview")).toURI());
        assertThrows(Exception.class, () -> ReviewYmlParser.parseReview(reviewDirectory));

    }

    @Test
    @SneakyThrows
    void parseReviewWithMissingFile() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(BlogApplication.class.getClassLoader().getResource("missingReview")).toURI());
        assertThrows(RuntimeException.class, ()-> ReviewYmlParser.parseReview(reviewDirectory));
    }

    @Test
    @SneakyThrows
    void parseReviewWithoutImage() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(BlogApplication.class.getClassLoader().getResource("validReviewNoImage")).toURI());
        Review result = ReviewYmlParser.parseReview(reviewDirectory);
        Review expectedReview = getNoImageReview();

        assertEquals(expectedReview, result);
    }

    private static Review getNoImageReview() {
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