package by.dudko.questionnaires.service;

public interface EmailService {
    void sendEmailVerificationMessage(String recipient, String verificationCode);

    void sendPasswordChangedMessage(String recipient);

    void sendResetPasswordMessage(String recipient, String verificationCode);

    void sendEmail(String subject, String content, String recipient);
}
