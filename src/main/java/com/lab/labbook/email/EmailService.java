package com.lab.labbook.email;

import com.lab.labbook.entity.Log;
import com.lab.labbook.entity.LogLevel;
import com.lab.labbook.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final LogRepository logRepository;

    public void sendMail(final Mail mail) {
        try {
            javaMailSender.send(createMailMessage(mail));
        } catch (MailException e) {
            Log log = new Log(LogLevel.ERROR.name(), mail.toString(), e.getMessage());
            logRepository.save(log);
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        if (mail.getToCc() != null && mail.getToCc().length() > 0) {
            mailMessage.setCc(mail.getToCc());
        }
        return mailMessage;
    }
}
