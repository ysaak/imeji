package ysaak.imeji.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApplicationConfig applicationConfig;

    @Autowired
    public WebMvcConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/wallpapers/full/**").addResourceLocations("file:" + applicationConfig.getWallpaperStoragePath()).setCachePeriod(0);
        registry.addResourceHandler("/wallpapers/thumbnail/**").addResourceLocations("file:" + applicationConfig.getThumbnailStoragePath()).setCachePeriod(0);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").setCachePeriod(0);
    }
}
