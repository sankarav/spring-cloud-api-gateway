package edu.san.springcloudapigateway.filters;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;

@Value
@Builder
public class CachedRequest {
  RequestPath path;
  HttpMethod method;
  MultiValueMap<String, String> queryParams;

  static CachedRequest of(ServerHttpRequest request) {
    return builder()
        .method(request.getMethod())
        .path(request.getPath())
        .queryParams(request.getQueryParams())
        .build();
  }

}
