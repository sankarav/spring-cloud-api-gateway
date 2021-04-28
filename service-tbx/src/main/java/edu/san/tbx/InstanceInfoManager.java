package edu.san.tbx;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.eureka.http.RestTemplateEurekaHttpClient;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class InstanceInfoManager {

  private final InstanceInfoRepo repo;
  private final RestTemplateEurekaHttpClient eurekaHttpClient;

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
