package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private final JavaMailSender sender;
    private final Configuration configuration;

    @Value("${app.server.url}")
    private final String appUrl;

    @Override
    public void sendEmailVerificationMessage(long userId, String recipient, String verificationCode) {
        String link = String.format("%s/api/users/%d/verification?code=%s", appUrl, userId, verificationCode);
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("verificationLink", link);
            Template template = configuration.getTemplate("confirm_registration.ftl");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            sendEmail("Confirm Registration", content, recipient);
        } catch (IOException | TemplateException e) {
            handleMailExceptions(e);
        }
    }

    @Override
    public void sendPasswordChangedMessage(String recipient) {
        try {
            Map<String, Object> model = new HashMap<>();
            model.put("email", recipient);
            Template template = configuration.getTemplate("password_changed.ftl");
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            sendEmail("Password change", content, recipient);
        } catch (IOException | TemplateException e) {
            handleMailExceptions(e);
        }
    }

    @Override
    public void sendEmail(String subject, String content, String recipient) {
        try {
            MimeMessage message = sender.createMimeMessage();
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setHeader("Content-Type", CONTENT_TYPE);
            message.setSubject(subject, "UTF-8");
            message.setContent(content, CONTENT_TYPE);
            Thread thread = new Thread(() -> sender.send(message));
            thread.start();
        } catch (MessagingException e) {
            log.error(String.format("Failed to send the message to %s", recipient), e);
        }
    }

    private void handleMailExceptions(Exception e) {
        log.error("Something bad happened with email template", e);
        throw new RuntimeException(e);
    }
}
