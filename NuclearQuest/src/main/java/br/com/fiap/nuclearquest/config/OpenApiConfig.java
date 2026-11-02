package br.com.fiap.nuclearquest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI nuclearQuestOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("NuclearQuest API")
                .version("1.0")
                .description("API RESTful de um quiz gamificado que apresenta as aplicações "
                        + "e os benefícios da tecnologia nuclear. Challenge FIAP 2026 / CTMSP."));
    }
}