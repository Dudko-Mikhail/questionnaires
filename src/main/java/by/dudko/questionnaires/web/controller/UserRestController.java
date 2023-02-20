package by.dudko.questionnaires.web.controller;

import by.dudko.questionnaires.dto.PageResponse;
import by.dudko.questionnaires.dto.error.ErrorResponse;
import by.dudko.questionnaires.dto.user.UserChangePasswordDto;
import by.dudko.questionnaires.dto.user.UserReadDto;
import by.dudko.questionnaires.service.UserService;
import by.dudko.questionnaires.validation.EmailUniquenessValidatorForUserEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final EmailUniquenessValidatorForUserEditDto validator;

    @GetMapping
    public PageResponse<UserReadDto> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("principal.id == #id")
    public UserReadDto findById(@PathVariable long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/verification")
    public ResponseEntity<Object> activateAccount(@PathVariable long id,
                                                  @RequestParam("code") String verificationCode) {
         return userService.activateAccount(id, verificationCode) ? ResponseEntity.noContent().build()
                 : ResponseEntity.badRequest().body(ErrorResponse.of("Invalid verification code"));
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("principal.id == #id")
    public ResponseEntity<Object> changePassword(@PathVariable long id,
                                                 @RequestBody @Validated UserChangePasswordDto changePasswordDto) {

        return userService.changePassword(id, changePasswordDto) ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().body(ErrorResponse.of("Old password is invalid"));
    }
}
