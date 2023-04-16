package ru.tsu.hits.authservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/access-token")
    @CrossOrigin
    public TokenDto getAccessToken(@RequestBody CredentialsDto dto) {
        return service.getAccessToken(dto);
    }

    @PostMapping("/refresh-token")
    @CrossOrigin
    public TokenDto getRefreshToken(@RequestBody RefreshTokenDto token) {
        return service.getRefreshToken(token);
    }

    @PostMapping("/logout")
    @CrossOrigin
    public void logout(@RequestBody RefreshTokenDto token) {
        service.logout(token);
    }
}
