package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
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
        Review expectedReview = getValidReview(reviewDirectory);

        assertEquals(expectedReview, result);
    }

    private static Review getValidReview(Path reviewDirectory) {
        Review expectedReview = new Review();
        expectedReview.setShop("TestShop");
        expectedReview.setDish("TestDish");
        expectedReview.setReviewers(List.of("Test1", "Test2"));
        expectedReview.setScoreBroth(5f);
        expectedReview.setScoreNoodles(3.5f);
        expectedReview.setScoreToppings(4f);
        expectedReview.setScoreAtmosphere(2f);
        expectedReview.setLocation("TestLocation");
        expectedReview.setPicturePath(Paths.get("src", "main", "resources", "reviews", "validReview","thumbnail.png").toString());
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

}