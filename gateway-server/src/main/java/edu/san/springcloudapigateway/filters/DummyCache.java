package edu.san.springcloudapigateway.filters;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class DummyCache {

  private final static Map<CachedRequest, ServerHttpResponse> CACHE = new ConcurrentHashMap<>();

  public Optional<ServerHttpResponse> get(CachedRequest req) {
    return Optional.ofNullable(CACHE.get(req));
  }

  public void put(CachedRequest k, ServerHttpResponse v) {
    CACHE.put(k, v);
  }

}
