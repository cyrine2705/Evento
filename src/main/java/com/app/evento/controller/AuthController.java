package com.app.evento.controller;

import com.app.evento.dto.AuthRequestDto;
import com.app.evento.dto.UserDto;
import com.app.evento.enums.Role;
import com.app.evento.models.MessageResponse;
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
    public ResponseEntity<String> authRequest(@RequestBody AuthRequestDto authRequestDto) {
        log.info("tawa bech nodo5lou lel app ", authRequestDto);
        var userRegistrationResponse = authService.authRequest(authRequestDto);
        log.info("Yey hana d5alna", userRegistrationResponse);
        return userRegistrationResponse;
    }
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody UserDto signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            log.debug("username mestaamel");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
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
    public ResponseEntity<?> resetPassword(@RequestBody String email, HttpServletRequest request) throws Exception {
        try {
            authService.resetPassword(email, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
