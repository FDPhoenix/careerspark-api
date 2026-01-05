package com.khanghn.careerspark_api.service.email;

import com.khanghn.careerspark_api.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.template-path}")
    private String templatePath;

    private String cacheTemplate;

    @PostConstruct
    private void loadTemplate() {
        try {
            cacheTemplate =  new String(Objects.requireNonNull(getClass()
                            .getClassLoader()
                            .getResourceAsStream(templatePath)).readAllBytes()
            );

            log.info("Email template loaded");
        } catch (IOException e) {
            log.error("Failed to load template", e);
        }
    }

    @Async("emailExecutor")
    @Override
    public void sendOtpEmail(User user, String otp) {
        try {
            if (cacheTemplate == null) {
                throw new FileNotFoundException("Template not found");
            }

            String content = cacheTemplate
                    .replace("{{OTP}}", otp)
                    .replace("{{EXPIRE_MINUTES}}", String.valueOf(10));

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("[CareerSpark] Verification OTP");
            helper.setText(content, true);

            mailSender.send(message);
        }
        catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }
}
