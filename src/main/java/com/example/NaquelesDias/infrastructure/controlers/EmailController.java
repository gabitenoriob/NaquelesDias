package com.example.NaquelesDias.infrastructure.controlers;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.NaquelesDias.model.email.Email;
import com.example.NaquelesDias.service.EmailService;
import com.example.NaquelesDias.service.security.TokenService;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private TokenService tokenService;
    @Autowired
    EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Email> sendEmail(@NonNull HttpServletRequest request) {
        logger.info("-Starting Sending Email-");

        String subject = "Notificação Naqueles Dias";
        String body = "Saudações!\n\n" +
                        "Você está recebendo esse Email porque habilitou a opção de notificação" +
                " do Naqueles Dias plataforma digital dedicada ao cuidado e à promoção da sua " +
                "saúde em todas as fases da vida. \n" +
                        "Futuramente, você receberá informativos personalizados com o seu perfil.\n\n" +
                        "Até breve.\n\n" +
                        "Equipe Naqueles Dias.";

        String token = tokenService.recoverToken(request);
        String userEmail = tokenService.getEmailFromToken(token);

        Email email = new Email("Larissa", userEmail, subject, body);
        logger.info("Email: {}", email);
        emailService.sendEmail(email);

        logger.info("-Email Sent Successfully-");

        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }
}