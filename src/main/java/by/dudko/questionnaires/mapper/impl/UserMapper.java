package by.dudko.questionnaires.mapper.impl;

import by.dudko.questionnaires.dto.user.UserDto;
import by.dudko.questionnaires.mapper.UniversalMapper;
import by.dudko.questionnaires.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class UserMapper implements UniversalMapper<User, UserDto> {
    private final PasswordEncoder passwordEncoder;

    @Override
    public Supplier<User> sourceGenerator() {
        return User::new;
    }

    @Override
    public Supplier<UserDto> targetGenerator() {
        return UserDto::new;
    }

    @Override
    public UserDto map(User source, UserDto target) {
        target.setId(source.getId());
        target.setEmail(source.getEmail());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPhoneNumber(source.getPhoneNumber());
        return target;
    }

    @Override
    public User reverseMap(UserDto source, User target) {
        target.setEmail(source.getEmail());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setPhoneNumber(source.getPhoneNumber());
        String password = source.getPassword();
        if (password != null) {
            target.setPassword(passwordEncoder.encode(password));
        }
        return target;
    }
}
