package com.app.evento.controller;

import com.app.evento.payload.AuthRequest;
import com.app.evento.dto.UserDto;
import com.app.evento.payload.MessageResponse;
import com.app.evento.models.User;
import com.app.evento.repositories.UserRepository;
import com.app.evento.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor

public class AuthController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequestDto) {
        log.info("tawa bech nodo5lou lel app ");
       return authService.authRequest(authRequestDto);


    }
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody UserDto signUpRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
            log.debug("username mestaamel");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
            log.debug("email mestaamel");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .role(signUpRequest.getRole())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();


        userRepository.save(user);
        log.info("yees marahbe bik user jdid");
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/request-password")
    public ResponseEntity<?> requestPassword(@RequestBody String email) throws Exception {
        try {
            authService.requestChangePassword(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPasswordWithToken(@RequestParam String token, @RequestBody  String password) throws Exception {
        authService.changePassword(token, password);
        return ResponseEntity.ok().build();
    }
    @PostMapping( "verifyToken/{token}")
    public ResponseEntity<?> processResetPassword(@PathVariable String token) {
        try {

            authService.verifyTokenExpiration(token);


            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
