package com.ramenreviewers.blog;

import com.ramenreviewers.blog.model.Review;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.List;

/** Class parsing review data for a thyme leaf template */
public class ReviewTemplateProcessor {

    public static String processTemplate(List<Review> Reviews) {
        var templateEngine = createTemplateEngine();
        Context context = new Context();
        context.setVariable("reviews", Reviews);
        return templateEngine.process("template", context);
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        var templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

}
