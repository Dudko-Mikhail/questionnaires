package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.auth.AuthenticationResponse;
import by.dudko.questionnaires.dto.auth.Credentials;
import by.dudko.questionnaires.dto.user.UserCreateDto;
import by.dudko.questionnaires.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final UserService userService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Validated Credentials credentials) {
        return userService.login(credentials);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@RequestBody @Validated UserCreateDto createDto) {
        userService.signUp(createDto);
    }
}
