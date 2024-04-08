package in.nucleusteq.plasma.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Bean
    Path fileStorageLocation() {
        return Paths.get(uploadDir).toAbsolutePath().normalize();
    }
}
