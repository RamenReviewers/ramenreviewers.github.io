package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ReviewYmlParserTest {

    @Test
    @SneakyThrows
    void shouldParseValidReview() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(PageGenerator.class.getClassLoader().getResource("validReview")).toURI());
        Review result = ReviewYmlParser.parseReview(reviewDirectory);
        Review expectedReview = Helper.getValidReview();

        assertEquals(expectedReview, result);
    }

    @Test
    @SneakyThrows
    void parsingReviewWithInvalidYAMLShouldThrowException() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(PageGenerator.class.getClassLoader().getResource("invalidReview")).toURI());
        assertThrows(Exception.class, () -> ReviewYmlParser.parseReview(reviewDirectory));

    }

    @Test
    @SneakyThrows
    void parsingReviewWithMissingFileShouldThrowException() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(PageGenerator.class.getClassLoader().getResource("missingReview")).toURI());
        assertThrows(RuntimeException.class, ()-> ReviewYmlParser.parseReview(reviewDirectory));
    }

    @Test
    @SneakyThrows
    void shouldParseValidReviewWithoutImage() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(PageGenerator.class.getClassLoader().getResource("validReviewNoImage")).toURI());
        Review result = ReviewYmlParser.parseReview(reviewDirectory);
        Review expectedReview = Helper.getValidNoImageReview();

        assertEquals(expectedReview, result);
    }



    @Test
    @SneakyThrows
    void parsingTooLongDescriptionShouldThrowException() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(PageGenerator.class.getClassLoader().getResource("longDescriptionReview")).toURI());
        assertThrows(Exception.class, () -> ReviewYmlParser.parseReview(reviewDirectory));
    }

}