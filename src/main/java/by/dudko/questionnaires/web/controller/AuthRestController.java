package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.auth.AuthenticationResponse;
import by.dudko.questionnaires.dto.auth.Credentials;
import by.dudko.questionnaires.dto.user.UserDto;
import by.dudko.questionnaires.service.UserService;
import by.dudko.questionnaires.validation.action.Create;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;

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
    public void signUp(@RequestBody @Validated({Create.class, Default.class}) UserDto userDto) {
        userService.signUp(userDto);
    }
}
