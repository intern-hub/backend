package internhub;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j

class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(CompanyRepository companyRepo, PositionRepository positionRepo) {
        return args -> {
        };
    }
}
