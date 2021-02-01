package nl.conspect.drivedok.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
public class ObjectMapperConfig {

    @Bean
    @Scope(SCOPE_SINGLETON)
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
