package by.dudko.questionnaires.mapper.impl.user;

import by.dudko.questionnaires.dto.user.UserEditDto;
import by.dudko.questionnaires.mapper.MapperWithTargetObject;
import by.dudko.questionnaires.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserEditMapper implements MapperWithTargetObject<UserEditDto, User> {
    @Override
    public User map(UserEditDto source, User target) {
        target.setEmail(source.getEmail());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        return target;
    }
}
