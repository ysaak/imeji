package ysaak.imeji.config;

import com.mitchellbosecke.pebble.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ysaak.imeji.view.helper.ViewExtension;

import java.io.File;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private final ApplicationConfig applicationConfig;

    @Bean
    public Extension viewHelperExtension() {
        return new ViewExtension();
    }

    @Autowired
    public WebMvcConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/wallpapers/full/**")
                .addResourceLocations("file:" + applicationConfig.getWallpaperStoragePath().toAbsolutePath() + File.separator)
                .setCachePeriod(0);

        registry.addResourceHandler("/wallpapers/thumbnail/**")
                .addResourceLocations("file:" + applicationConfig.getThumbnailStoragePath().toAbsolutePath() + File.separator)
                .setCachePeriod(0);

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").setCachePeriod(0);
    }
}
