package account.mapper;

import account.dto.UserDto;
import account.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public UserDto toUserDto(User user) {
        return MODEL_MAPPER.map(user, UserDto.class);
    }
}
