package com.zdpang.template.config;

import com.zdpang.template.interceptor.AuthWebRequestInterceptor;
import com.zdpang.template.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.MultipartConfigElement;

@Configuration
@ConfigurationProperties(prefix = "com.zdpang.template.config")
@Slf4j
public class AuthWebMvcBoot extends WebMvcConfigurationSupport {
    @Value("${env.config.oss.path}")
    private String ossPath;

    @Autowired
    private AuthWebRequestInterceptor authWebRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(authWebRequestInterceptor);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大KB,MB
        factory.setMaxFileSize("64MB");
        return factory.createMultipartConfig();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String filePath = "file:" + PathUtil.getJarPath() + ossPath + "/";
        String RESOURCE_LOCATION = "classpath:/META-INF/resources/";
        log.info("pathUtil.getJarPath():" + PathUtil.getJarPath());
        log.info("filePath:" + filePath);
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations(RESOURCE_LOCATION);
        registry.addResourceHandler("/webjars/**").addResourceLocations(RESOURCE_LOCATION + "webjars/");
        registry.addResourceHandler(ossPath + "/**").addResourceLocations(filePath);

        super.addResourceHandlers(registry);
    }
}