package edu.san.tbx;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.http.RestTemplateEurekaHttpClient;
import org.springframework.cloud.netflix.eureka.http.WebClientEurekaHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InstanceInfoManager {

  private final InstanceInfoRepo repo;
  private final RestTemplateEurekaHttpClient eurekaHttpClient;

  public InstanceInfoManager(InstanceInfoRepo repo,
      RestTemplateEurekaHttpClient eurekaHttpClient
//      RestTemplate restTemplate,
//      @Value("${eureka.server.serviceUrl}") String serviceUrl
  ) {
    this.repo = repo;
    this.eurekaHttpClient = eurekaHttpClient;
  }

  public void registerAllApps() {
    repo.getAll().stream()
        .forEach(eurekaHttpClient::register);
  }

  public void cancelAllApps() {
    repo.getAll().stream()
        .forEach(instanceInfo ->
            eurekaHttpClient.cancel(instanceInfo.getAppName(), instanceInfo.getId()));
  }

}
