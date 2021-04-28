package edu.san.springcloudapigateway.filters;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.Builder;
import lombok.Value;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Inspired from this code: https://stackoverflow.com/questions/65498001/spring-cloud-gateway-with-spring-cache-and-caffeine
 */
@Component
public class CacheResponseGatewayFilterFactory
    extends AbstractGatewayFilterFactory<CacheResponseGatewayFilterFactory.Config> {

  private final DummyCache cache;

  public CacheResponseGatewayFilterFactory(DummyCache cache) {
    super(CacheResponseGatewayFilterFactory.Config.class);
    this.cache = cache;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return new GatewayFilter() {
      @Override
      public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Optional<ServerHttpResponse> cachedResp = cache
            .get(CachedRequest.of(exchange.getRequest()));
        if (cachedResp.isPresent()) {
          return chain.filter(
              exchange.mutate().response(cachedResp.get()).build()
          ); //Refer: ModifyResponseBodyGatewayFilterFactory
        }

        return chain.filter(exchange)
            .then(Mono.fromRunnable(() -> System.out.println("Noop: returning response as is")));

        /*
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        final Optional<CachedResponse> cachedResponse =
            getCachedResponse(CachedRequest.of(exchange.getRequest()));
        if (cachedResponse.isPresent()) {
          ServerHttpResponse serverHttpResponse = exchange.getResponse();
          serverHttpResponse.setStatusCode(cachedResponse.get().getHttpStatus());
          serverHttpResponse.getHeaders().addAll(cachedResponse.get().getHeaders());
          DataBuffer buffer = exchange.getResponse().bufferFactory()
              .wrap(cachedResponse.get().getBody());

          return exchange.getResponse().writeWith(Flux.just(buffer));
        }

      }));
      */
      }
    };
  }

  //TODO: Check if cache has response and return
  // returns dummy stub now
  private static Optional<CachedResponse> getCachedResponse(CachedRequest req) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Gateway", "Hello!!!");
    return Optional.ofNullable(
        CachedResponse.builder()
            .httpStatus(HttpStatus.OK)
            .headers(headers)
            .body("Hello from Gateway".getBytes(StandardCharsets.UTF_8))
            .build()
    ) ;
  }

  public static class Config {

  }

  @Value
  @Builder
  private static class CachedResponse {

    HttpStatus httpStatus;
    HttpHeaders headers;
    byte[] body;
  }
}
