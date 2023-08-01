package com.example.JWT.auth;


import com.example.JWT.Config.JwtService;
import com.example.JWT.Entity.Role;
import com.example.JWT.Entity.User;
import com.example.JWT.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private  final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        var user = User.builder()
                .uname(request.getUname())
                .uemail(request.getUemail())
                .upassword(passwordEncoder.encode(request.getUpassword()))
                .role(Role.ADMIN)
                .build();
/*
        userRepo.save(user);
        var jwttoken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwttoken)
                .build();*/
        return userRepo.save(user);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUemail(),
                        request.getUpassword()
                )
        );
        var user = userRepo.findByUemail(request.getUemail()).orElseThrow(RuntimeException::new);
        var jwtToken = jwtService.generateToken(user);
        var role = user.getRole();
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(role.name())
                .build();
    }
}
