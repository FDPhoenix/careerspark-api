package com.khanghn.careerspark_api.service.email;

import com.khanghn.careerspark_api.model.User;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    @Override
    public String loadTemplate(String path) {
        try {
            return new String(Objects.requireNonNull(getClass()
                            .getClassLoader()
                            .getResourceAsStream(path)).readAllBytes()
            );
        } catch (IOException e) {
            log.error("Failed to load template", e);
            return null;
        }
    }

    @Override
    public void sendOtpEmail(User user, String otp) {
        try {
            String template = loadTemplate(templatePath);

            if (Objects.isNull(template)) {
                throw new FileNotFoundException("Template not found");
            }

            template = template
                    .replace("{{OTP}}", otp)
                    .replace("{{EXPIRE_MINUTES}}", String.valueOf(10));

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("[Careerspark] Verification OTP");
            helper.setText(template, true);

            mailSender.send(message);
        }
        catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }
}
