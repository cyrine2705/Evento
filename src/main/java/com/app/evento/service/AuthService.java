package com.app.evento.service;

import com.app.evento.payload.AuthRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
     ResponseEntity<String> authRequest(AuthRequest authRequestDto);
     void requestChangePassword(String email ) throws Exception;
     void changePassword(String token, String newPassword) throws Exception;

}
