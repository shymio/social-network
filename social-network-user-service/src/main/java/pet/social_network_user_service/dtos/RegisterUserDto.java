package pet.social_network_user_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterUserDto {
    private String email;
    private String password;
    private String fullName;
}
