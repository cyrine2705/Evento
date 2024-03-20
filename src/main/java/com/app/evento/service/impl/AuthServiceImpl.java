package com.app.evento.service.impl;

import com.app.evento.dto.AuthRequestDto;
import com.app.evento.models.User;
import com.app.evento.repositories.UserRepository;
import com.app.evento.service.AuthService;
import com.app.evento.service.EmailService;
import com.app.evento.service.JwtService;
import com.app.evento.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j //hedhi annotation taa log
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private static final long TOKEN_EXPIRATION_TIME = 30 * 60 * 1000;
    @Override
    public ResponseEntity<String> authRequest(AuthRequestDto authRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(authRequestDto.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("this email doesnt exist");
        }
        User user = userOptional.get();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), authRequestDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            log.info("Yey hana d5alna");
            return ResponseEntity.ok().body(getToken(userDetails));
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            } else {
                log.error("Authentication failed:", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication error");
            }
        }
    }

    public String getToken( UserDetails userDetails) {
        final var roles = userDetails.getAuthorities();
        final var username =  userDetails.getUsername();
        return jwtService.generateToken(Map.of("role", roles), username);

    }
    public void resetPassword(String email , HttpServletRequest request) throws Exception {
       User user = userRepository.findByEmail(email.replace("\"","")).orElse(null);

        if (user == null) {
            throw new Exception("User not found");
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME));
        userRepository.save(user);
        String resetPasswordLink = ServletUtil.getSiteURL(request) + "/reset_password?token=" + token;
        emailService.sendResetPasswordEmail(user.getEmail(),email, resetPasswordLink,user.getFirstName());
    }
}
