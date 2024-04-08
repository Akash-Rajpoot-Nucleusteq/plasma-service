package in.nucleusteq.plasma.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import in.nucleusteq.plasma.payload.CustomMultipartFile;
import in.nucleusteq.plasma.payload.CustomMultipartFileSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(CustomMultipartFile.class,
                new CustomMultipartFileSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
