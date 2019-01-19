package com.siaron.config;

import com.siaron.version.ApiRequestMappingHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UrlPathHelper;


/**
 * @author xielongwang
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ApiRequestMappingHandlerMapping();
    }
}
