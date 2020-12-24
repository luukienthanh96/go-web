package com.goweb.webapp.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/bootstrap/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/4.1.1/");
		
		registry.addResourceHandler("/resources/jquery/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.4.1/");
		
		registry.addResourceHandler("/resources/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/resources/fonts/**").addResourceLocations("classpath:/static/fonts/");
		registry.addResourceHandler("/resources/images/**").addResourceLocations("classpath:/static/images/");
		registry.addResourceHandler("/resources/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/resources/vendor/**").addResourceLocations("classpath:/static/vendor/");
	}

}
