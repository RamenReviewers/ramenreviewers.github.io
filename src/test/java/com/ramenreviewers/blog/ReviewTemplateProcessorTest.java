package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

class ReviewTemplateProcessorTest {

    @Test
    void shouldProcessListOfValidReviews(){

        List<Review> list = Instancio.ofList(Review.class).size(10).create();
        ReviewTemplateProcessor.processTemplate(list);
    }
}