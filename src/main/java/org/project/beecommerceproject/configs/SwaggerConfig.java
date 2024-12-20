package org.project.beecommerceproject.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.app.name}")
    private String swaggerAppName;
    @Value("${swagger.add.security.name}")
    private String swaggerAddSecurityName;
    @Value("${swagger.scheme}")
    private String swaggerScheme;
    @Value("${swagger.format}")
    private String swaggerFormat;
    @Value("${swagger.dev.url}") private String swaggerDevUrl;
    @Value("${swagger.pro.url}") private String swaggerProUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(swaggerDevUrl);
        devServer.description("Server URL in Development Environment");

        Server proServer = new Server();
        proServer.setUrl(swaggerProUrl);
        proServer.description("Server URL in Production Environment");

        Contact contact = new Contact();
        contact.setEmail("adronghia@gmail.com");
        contact.setName("Nghia_TLM");

        Info info = new Info()
                .title(swaggerAppName)
                .version("3.0")
                .contact(contact)
                .description("Those API belo to manage config web-app");

        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList(swaggerAddSecurityName);

        Components components = new Components();
        components.addSecuritySchemes(swaggerAddSecurityName, new SecurityScheme().name(swaggerAddSecurityName).type(SecurityScheme.Type.HTTP).scheme(swaggerScheme).bearerFormat(swaggerFormat));

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, proServer))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
