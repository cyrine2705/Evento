package com.app.evento.service;

public interface EmailService {
    void send(String to, String email);
    void sendResetPasswordEmail(String to, String email,String resetLink,String firstname);
}
