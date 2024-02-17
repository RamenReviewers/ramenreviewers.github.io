package com.ramenreviewers.blog;

import org.junit.jupiter.api.Test;

import java.util.List;

class ReviewTemplateProcessorTest {

    @Test
    void shouldProcessListOfValidReviews(){
        var reviews = List.of(TestUtil.getValidReview());
        ReviewTemplateProcessor.processTemplate(reviews);
    }
}