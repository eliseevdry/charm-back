package ru.eliseev.charm.back.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("ru.eliseev.charm.back")
@EnableWebMvc
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Настраиваем обработчик для ресурсов из папки /img/
        registry.addResourceHandler("/img/**") // URL паттерн
            .addResourceLocations("/img/"); // Физическое расположение в приложении (например, webapp/img/)
        // Можно также добавить / или /WEB-INF/img/ или classpath:/static/img/
    }
}
