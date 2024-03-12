package com.app.evento.service;

import com.app.evento.dto.AuthRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
     ResponseEntity<String> authRequest(AuthRequestDto authRequestDto);
     void resetPassword(String email , HttpServletRequest request) throws Exception;

}
