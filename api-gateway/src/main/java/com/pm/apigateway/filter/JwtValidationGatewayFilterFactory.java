package com.pm.apigateway.filter;


import com.pm.apigateway.dto.AuthenticatedUserResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;


    public JwtValidationGatewayFilterFactory(WebClient.Builder webclientBuilder ,
        @Value("${auth.service.url}") String authServiceUrl) {

        this.webClient = webclientBuilder.baseUrl(authServiceUrl).build();
    }



    @Override
    public GatewayFilter apply(Object config){
           return ((exchange, chain) -> {
               String token  = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
               //exchange variable is a hava object that represents the current request
               if(token == null || !token.startsWith("Bearer ")){
                   exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                   return exchange.getResponse().setComplete();
               }
               return webClient.get()
                       .uri("/validate")
                       .header(HttpHeaders.AUTHORIZATION, token)
                       .retrieve()
                       .bodyToMono(AuthenticatedUserResponseDTO.class)
                       .flatMap(user -> {

                           ServerHttpRequest request = exchange.getRequest()
                                   .mutate()
                                   .header("X-User-Id", user.userId().toString())
                                   .header("X-User-Email", user.email())
                                   .header("X-User-Role", user.role())
                                   .build();

                           return chain.filter(
                                   exchange.mutate()
                                           .request(request)
                                           .build()
                           );
                       })
                       .onErrorResume(e -> {
                           exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                           return exchange.getResponse().setComplete();
                       });


           });
    }

}
