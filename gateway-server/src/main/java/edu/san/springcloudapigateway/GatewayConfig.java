package edu.san.springcloudapigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(p -> p
            .path("/get")
            .filters(f -> f.addRequestHeader("Hello", "World"))
            .uri("http://httpbin.org:80"))
        .route("country", p -> p
            .path("/country", "/org/v1/country")
            .filters(f -> f.addRequestHeader("X-gateway-screened", "true")
                           .addResponseHeader("X-gateway-screened", "true")
                           .setPath("/org/v1/country")
                           //.rewritePath("/?(?<segment>.*)", "/org/v1/country")
            )
            .uri("http://localhost:8081")
        )
        .build();
  }

}
