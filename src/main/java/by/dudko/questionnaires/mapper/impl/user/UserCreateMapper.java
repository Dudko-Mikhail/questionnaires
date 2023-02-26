package by.dudko.questionnaires.mapper.impl.user;

import by.dudko.questionnaires.dto.user.UserCreateDto;
import by.dudko.questionnaires.mapper.Mapper;
import by.dudko.questionnaires.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateDto source) {
        return User.builder()
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .password(passwordEncoder.encode(source.getPassword()))
                .verificationCode(UUID.randomUUID())
                .build();
    }
}
