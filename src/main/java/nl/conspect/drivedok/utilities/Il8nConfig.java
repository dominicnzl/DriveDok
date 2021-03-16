package nl.conspect.drivedok.utilities;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class Il8nConfig {

    @Bean
    public LocaleResolver localeResolver() {
        var resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                var interceptor = new LocaleChangeInterceptor();
                interceptor.setParamName("localeCode");
                registry.addInterceptor(interceptor);
            }
        };
    }

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("il8n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
