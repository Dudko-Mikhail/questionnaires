package by.dudko.questionnaires.service;

public interface EmailService {
    void sendEmailVerificationMessage(long userId, String recipient, String verificationCode);

    void sendPasswordChangedMessage(String recipient);

    void sendEmail(String subject, String content, String recipient);
}
