package com.app.evento.service.impl;

import com.app.evento.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class EmailServiceImpl implements EmailService {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("your account on Bee application");
            helper.setFrom("eventoapp2024@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    @Async
    public void sendResetPasswordEmail(String to, String email, String resetLink, String firstname) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);
            helper.setSubject("Reset your password on Evento App");
            helper.setFrom("beehr001@gmail.com");
            ClassPathResource resource = new ClassPathResource("emailTemplate/email.html");
            System.out.println("Resource path: " + resource.getPath());
            String emailBody = org.springframework.util.StreamUtils.
                    copyToString(
                            new org.springframework.core.io.ClassPathResource(
                                    "emailTemplate/email.html")
                                    .getInputStream(),
                            Charset.forName("UTF-8")
                    );
            emailBody = emailBody.replace("[RESET_PASSWORD_LINK]", resetLink)
                    .replace("[firstname]", firstname);

            helper.setText(emailBody, true);
            ClassPathResource logoResource = new ClassPathResource("emailTemplate/eventoLogo.png");
            ClassPathResource coverResource = new ClassPathResource("emailTemplate/eventoCover.png");
            ClassPathResource socialResource = new ClassPathResource("emailTemplate/insta_fb.png");

            MimeBodyPart logoPart = new MimeBodyPart();
            logoPart.setContent(logoResource.getInputStream(), "image/png");
            logoPart.setHeader("Content-ID", "<cid:logo>");  // Replace with unique identifier

            MimeBodyPart coverPart = new MimeBodyPart();
            coverPart.setContent(coverResource.getInputStream(), "image/png");
            coverPart.setHeader("Content-ID", "<cid:cover>");  // Replace with unique identifier

            MimeBodyPart socialPart = new MimeBodyPart();
            socialPart.setContent(socialResource.getInputStream(), "image/png");
            socialPart.setHeader("Content-ID", "<cid:social>");  // Replace with unique identifier
            helper.addInline("logo", logoResource);  // Replace with unique identifier
            helper.addInline("cover", coverResource);  // Replace with unique identifier
            helper.addInline("social", socialResource);
            mailSender.send(mimeMessage);

        } catch (MessagingException | IOException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    }

