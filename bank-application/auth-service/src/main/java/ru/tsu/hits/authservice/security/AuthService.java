package ru.tsu.hits.authservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final WebClient webClient;

    public TokenDto getAccessToken(CredentialsDto dto) {
        return webClient.post()
                .uri("http://host.docker.internal:8181/realms/bank-application-realm/protocol/openid-connect/token")
                .header(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(
                        BodyInserters.fromFormData("client_id", "bank-application-client")
                                .with("grant_type", "password")
                                .with("username", dto.getUsername())
                                .with("password", dto.getPassword())
                ).retrieve()
                .bodyToMono(TokenDto.class)
                .block();
    }

    public TokenDto getRefreshToken(RefreshTokenDto dto) {
        return webClient.post()
                .uri("http://host.docker.internal:8181/realms/bank-application-realm/protocol/openid-connect/token")
                .header(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(
                        BodyInserters.fromFormData("client_id", "bank-application-client")
                                .with("grant_type", "refresh_token")
                                .with("refresh_token", dto.getRefreshToken())
                ).retrieve()
                .bodyToMono(TokenDto.class)
                .block();
    }

    public void logout(RefreshTokenDto dto) {
        webClient.post()
                .uri("http://host.docker.internal:8181/realms/bank-application-realm/protocol/openid-connect/logout")
                .header(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(
                        BodyInserters.fromFormData("client_id", "bank-application-client")
                                .with("refresh_token", dto.getRefreshToken())
                ).retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
