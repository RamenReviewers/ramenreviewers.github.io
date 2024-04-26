package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ReviewParserTest {

    @Test
    @SneakyThrows
    void parseValidReview() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(
            ReviewPageGenerator.class.getClassLoader().getResource("validReview")).toURI());
        assertDoesNotThrow(() -> ReviewParser.parseReview(reviewDirectory));
    }

    @Test
    @SneakyThrows
    void parseReviewWithMissingFile() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(
            ReviewPageGenerator.class.getClassLoader().getResource("missingReview")).toURI());
        assertThrows(RuntimeException.class, ()-> ReviewParser.parseReview(reviewDirectory));
    }

    @Test
    @SneakyThrows
    void parseReviewWithoutImage() {
        Path reviewDirectory = Paths.get(Objects.requireNonNull(
            ReviewPageGenerator.class.getClassLoader().getResource("validReviewNoImage")).toURI());
        Review result = ReviewParser.parseReview(reviewDirectory);

        assertEquals(List.of("https://via.assets.so/img.jpg?w=400&h=200&tc=grey&bg=white&t=No%20Review%20Image"), result.getPicturePaths());
    }

}