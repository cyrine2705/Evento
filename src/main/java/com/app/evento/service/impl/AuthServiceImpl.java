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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
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
        String username =userRepository.findByEmail(authRequestDto.getEmail()).get().getUsername();
        try{

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, authRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final var userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok().body(getToken(userDetails));}
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
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
