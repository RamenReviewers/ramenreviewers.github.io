package com.example.blog;

import org.springframework.context.ApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.List;

/** Class parsing review data for a thyme leaf template */
public class ReviewTemplateProcessor {

    private TemplateEngine templateEngine;

    public ReviewTemplateProcessor(ApplicationContext context) {
        createTemplateEngine(context);
    }


    public String processTemplate(List<Review> Reviews) {

        // TODO delete this test code
        String title = "Dynamic Title";
        String content = "Dynamic Content";
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("content", content);
        return templateEngine.process("test", context);

        // TODO iterate through all reviews
            // TODO parse data
            // TODO add to root template?
            // TODO ???
            // TODO profit
    }


    private void createTemplateEngine(ApplicationContext context) {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(context);
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

}
