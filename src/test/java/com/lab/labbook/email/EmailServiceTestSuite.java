package com.lab.labbook.email;

import com.lab.labbook.config.DataConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EmailServiceTestSuite {

    @InjectMocks
    private EmailService emailService;

    @InjectMocks
    private DataConfig dataConfig;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void testSendMail() {
        //Given
        Mail mail = new Mail(dataConfig.getDefaultUser(), "", "Test", "TestMessage");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //When
        emailService.sendMail(mail);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

}
