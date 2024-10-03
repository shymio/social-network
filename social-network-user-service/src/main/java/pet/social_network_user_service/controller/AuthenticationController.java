package pet.social_network_user_service.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.social_network_user_service.dtos.LoginUserDto;
import pet.social_network_user_service.dtos.RegisterUserDto;
import pet.social_network_user_service.entity.User;
import pet.social_network_user_service.responses.LoginResponse;
import pet.social_network_user_service.service.AuthenticationService;
import pet.social_network_user_service.service.JwtService;

@RestController
@RequestMapping("/api/users/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticateUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticateUser, authenticateUser.getId());
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}


