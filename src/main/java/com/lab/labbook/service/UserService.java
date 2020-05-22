package com.lab.labbook.service;

import com.lab.labbook.config.DataConfig;
import com.lab.labbook.entity.User;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.repository.LabBookRepository;
import com.lab.labbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final DataConfig dataConfig;
    private final LabBookRepository labBookRepository;

    public List<User> getUsers() {
        return repository.findAll();
    }

    public List<User> getByName(String name) {
        return repository.findAllByName(name);
    }

    public User getById(Long id) {
        return getUserOrException(id);
    }

    public User getByLogin(String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.USER_NOT_FOUND, login));
    }

    public User saveUser(User user) {
        user.setDate(LocalDateTime.now());
        return repository.save(user);
    }

    public void deleteUser(User oldUser) {
        if (labBookRepository.existsByUser(oldUser)) {
            User newUser = getDefaultUser();
            labBookRepository.updateUserId(oldUser, newUser);
        }
        repository.delete(oldUser);
    }

    public User getUserOrException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.USER_NOT_FOUND, id.toString()));
    }

    private User getDefaultUser() {
        return repository.findByLogin(dataConfig.getDefaultUser())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.USER_NOT_FOUND, dataConfig.getDefaultUser()));
    }
}
