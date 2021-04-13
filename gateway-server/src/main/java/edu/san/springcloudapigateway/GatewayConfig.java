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
            .filters(f -> f.addRequestHeader("gateway-screened", "true")
                           .addResponseHeader("gateway-screened", "true")
                           .setPath("/org/v1/country")
                           //.rewritePath("/?(?<segment>.*)", "/org/v1/country")
            )
            .uri("http://localhost:8081")
        )
        /*.route(p -> p
            .host("*.hystrix.com")
            .filters(f ->
                f.hystrix(config -> config.setName("mycmd")))
            .uri("http://httpbin.org:80"))*/
        .build();
  }

}
