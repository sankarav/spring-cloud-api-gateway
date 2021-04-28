package edu.san.tbx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.http.RestTemplateEurekaHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  RestTemplateEurekaHttpClient eurekaHttpClient(
      RestTemplate restTemplate,
      @Value("${eureka.server.serviceUrl}") String serviceUrl
      ) {
    return new RestTemplateEurekaHttpClient(restTemplate, serviceUrl);
  }

}
