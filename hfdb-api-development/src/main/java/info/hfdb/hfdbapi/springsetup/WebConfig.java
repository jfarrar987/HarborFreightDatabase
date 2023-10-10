package info.hfdb.hfdbapi.springsetup;

import info.hfdb.hfdbapi.HfdbApiApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final String url = HfdbApiApplication.getLoc() + ":" + HfdbApiApplication.getPort();
        registry.addMapping("/**").allowedOrigins("http://" + url, "https://" + url).allowedMethods("GET", "POST");
    }
}
