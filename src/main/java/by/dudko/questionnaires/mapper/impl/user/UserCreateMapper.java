package by.dudko.questionnaires.mapper.impl.user;

import by.dudko.questionnaires.mapper.Mapper;
import by.dudko.questionnaires.dto.user.UserCreateDto;
import by.dudko.questionnaires.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreateMapper implements Mapper<UserCreateDto, User> {
    @Override
    public User map(UserCreateDto source) {
        return User.builder()
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
//                .password() // todo add encoder
                .build();
    }
}
