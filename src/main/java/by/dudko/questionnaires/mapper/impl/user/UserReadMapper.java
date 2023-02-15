package by.dudko.questionnaires.mapper.impl.user;

import by.dudko.questionnaires.dto.user.UserReadDto;
import by.dudko.questionnaires.mapper.Mapper;
import by.dudko.questionnaires.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User source) {
        return UserReadDto.builder()
                .id(source.getId())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
