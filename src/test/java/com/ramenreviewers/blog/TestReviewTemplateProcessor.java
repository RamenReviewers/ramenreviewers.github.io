package com.ramenreviewers.blog;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

class TestReviewTemplateProcessor {

    @Test
    void shouldProcessListOfValidReviews() {
        var reviews = List.of(TestUtil.getValidReview());
        ReviewTemplateProcessor.processTemplate(reviews);
    }

    @Test
    void willFail() {
        fail();
    }
}