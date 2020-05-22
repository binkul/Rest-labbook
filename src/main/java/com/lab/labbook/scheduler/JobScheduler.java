package com.lab.labbook.scheduler;

import com.lab.labbook.config.ApplicationConfig;
import com.lab.labbook.email.EmailService;
import com.lab.labbook.email.Mail;
import com.lab.labbook.entity.Status;
import com.lab.labbook.entity.User;
import com.lab.labbook.repository.LabBookRepository;
import com.lab.labbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JobScheduler {

    private final EmailService emailService;
    private final LabBookRepository labBookRepository;
    private final ApplicationConfig applicationConfig;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 23 * * *")
    public void sendStatistic() {
        List<User> observers = userRepository.findAllByObserver(true);
        for (User user : observers) {
            emailService.sendMail(prepareMail(user.getEmail()));
        }
    }

    @Scheduled(cron = "0 0 14 * * *")
    public void updateCurrencies() {
        applicationConfig.updateExchange();
    }

    private Mail prepareMail(String emailAddress) {
        String subject = "Everyday statistic from LabBook";
        String message = "Date: " + LocalDate.now().toString() + "\n";
        message += "Created new LabBooks total: " + labBookRepository.countByStatus(Status.CREATED.name()) + "\n";
        message += "Updated new LabBooks total: " + labBookRepository.countByStatus(Status.MODIFIED.name()) + "\n";
        message += "Deleted new LabBooks total: " + labBookRepository.countByStatus(Status.DELETED.name());
        return new Mail(emailAddress, "", subject, message);
    }
}
