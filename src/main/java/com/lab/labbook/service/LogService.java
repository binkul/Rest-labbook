package com.lab.labbook.service;

import com.lab.labbook.email.EmailService;
import com.lab.labbook.email.Mail;
import com.lab.labbook.entity.Log;
import com.lab.labbook.entity.User;
import com.lab.labbook.repository.LogRepository;
import com.lab.labbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository repository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public List<Log> getAll() {
        return repository.findAll();
    }

    public void save(Log log) {
        repository.save(log);
    }

    public void sendLog(Log log) {
        List<User> observers = userRepository.findAllByObserver(true);
        for (User user : observers) {
            emailService.sendMail(prepareMail(log, user.getEmail()));
        }
    }

    private Mail prepareMail(Log log, String emailAddress) {
        String subject = "Log from LabBook service: " + log.getLevel();
        String message = "Level: " + log.getLevel() + ", date: " + log.getDate() + "\n";
        message += log.getLog() + "\n";
        message += log.getComments();
        return new Mail(emailAddress, "", subject, message);
    }
}
