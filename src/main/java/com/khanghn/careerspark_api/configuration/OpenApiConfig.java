package com.khanghn.careerspark_api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(title = "CareerSpark API Docs", version = "1.0")
)

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "A JWT token is required to access this API. JWT token can be obtain by /auth/login " +
                "or the /auth/oauth2/authorization/{oAuthProvider} API"
)

public class OpenApiConfig {
}
