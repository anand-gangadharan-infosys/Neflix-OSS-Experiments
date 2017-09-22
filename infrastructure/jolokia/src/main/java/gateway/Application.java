package gateway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableZuulProxy
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
  @Bean
  public ZuulFallbackProvider cacheFallback() {
      return new ZuulFallbackProvider() {
          @Override
          public String getRoute() {
              return "cache";
          }

          @Override
          public ClientHttpResponse fallbackResponse() {
              return new ClientHttpResponse() {
                  @Override
                  public HttpStatus getStatusCode() throws IOException {
                      return HttpStatus.OK;
                  }

                  @Override
                  public int getRawStatusCode() throws IOException {
                      return 200;
                  }

                  @Override
                  public String getStatusText() throws IOException {
                      return "OK";
                  }

                  @Override
                  public void close() {

                  }

                  @Override
                  public InputStream getBody() throws IOException {
                      return new ByteArrayInputStream("Faced delay in connecting to cache service, please retry".getBytes());
                  }

                  @Override
                  public HttpHeaders getHeaders() {
                      HttpHeaders headers = new HttpHeaders();
                      headers.setContentType(MediaType.APPLICATION_JSON);
                      return headers;
                  }
              };
          }
      };
  }
  @Bean
  public ZuulFallbackProvider pinFallback() {
      return new ZuulFallbackProvider() {
          @Override
          public String getRoute() {
              return "pin";
          }

          @Override
          public ClientHttpResponse fallbackResponse() {
              return new ClientHttpResponse() {
                  @Override
                  public HttpStatus getStatusCode() throws IOException {
                      return HttpStatus.OK;
                  }

                  @Override
                  public int getRawStatusCode() throws IOException {
                      return 200;
                  }

                  @Override
                  public String getStatusText() throws IOException {
                      return "OK";
                  }

                  @Override
                  public void close() {

                  }

                  @Override
                  public InputStream getBody() throws IOException {
                      return new ByteArrayInputStream("Faced delay in connecting to pin service, please retry".getBytes());
                  }

                  @Override
                  public HttpHeaders getHeaders() {
                      HttpHeaders headers = new HttpHeaders();
                      headers.setContentType(MediaType.APPLICATION_JSON);
                      return headers;
                  }
              };
          }
      };
  }
  
  @Bean
  public CorsFilter corsFilter() {
      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      final CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);
      config.addAllowedOrigin("*");
      config.addAllowedHeader("*");
      config.addAllowedMethod("OPTIONS");
      config.addAllowedMethod("HEAD");
      config.addAllowedMethod("GET");
      config.addAllowedMethod("PUT");
      config.addAllowedMethod("POST");
      config.addAllowedMethod("DELETE");
      config.addAllowedMethod("PATCH");
      source.registerCorsConfiguration("/**", config);
      return new CorsFilter(source);
  }
}
