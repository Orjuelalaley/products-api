package nuam.productsapi.configuration;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("product-api")
                .addOpenApiCustomizer(openApi -> openApi
                        .info(new Info()
                                .title("Product API")
                                .description("API para la gestión de productos")
                                .version("1.0.0")))
                .pathsToMatch("/products/**")
                .build();
    }
}