package com.lab.labbook.service;

import com.lab.labbook.entity.*;
import com.lab.labbook.entity.dto.LabBookDto;
import com.lab.labbook.entity.extended.LabBookExtDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.mapper.LabBookMapper;
import com.lab.labbook.repository.LabBookRepository;
import com.lab.labbook.validator.LabBookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@EnableAspectJAutoProxy
@Service
public class LabBookService {
    private final LabBookRepository repository;
    private final LabBookMapper mapper;
    private final LabBookValidator validator;
    private final UserService userService;
    private final SeriesService seriesService;

    public List<LabBookExtDto> getAll() {
        return mapper.mapToExtListDto(repository.findAllByOrderById());
    }

    public List<LabBookExtDto> getByTitle(String title) {
        return mapper.mapToExtListDto(repository.findAllByTitleOrderById(title));
    }

    public List<LabBookExtDto> getByUser(Long userId) {
        User user = userService.getById(userId);
        return mapper.mapToExtListDto(repository.findByUserAndStatusIsNotOrderById(user, Status.DELETED.name()));
    }

    public List<LabBookExtDto> getByUserAndTitle(Long userId, String title) {
        User user = userService.getById(userId);
        String role = user.getRole();
        List<LabBook> labBooks;
        if (role.equals(Role.ADMIN.name())) {
            labBooks = repository.findAllByTitleOrderById(title);
        } else if (role.equals(Role.MODERATOR.name())) {
            labBooks = repository.findAllByTitleAndStatusNotLikeOrderById(title, Status.DELETED.name());
        } else {
            labBooks = repository.findAllByUserAndTitleAndStatusNotLikeOrderById(user, title, Status.DELETED.name());
        }
        return mapper.mapToExtListDto(labBooks);
    }

    public LabBookExtDto getById(Long id) {
        return mapper.mapToExtDto(getLabBook(id));
    }

    public void create(LabBookDto labBookDto) {
        LabBook labBook = validator.validateLabBook(labBookDto);
        repository.save(labBook);
    }

    public LabBookExtDto update(Map<String, String> updates, Long labId) {
        LabBook labBook = getLabBook(labId);
        validator.validateDeleted(labBook);
        if (updates.containsKey("title")) {
            labBook.setTitle(updates.get("title"));
        }
        if (updates.containsKey("description")) {
            labBook.setDescription(updates.get("description"));
        }
        if (updates.containsKey("conclusion")) {
            labBook.setConclusion(updates.get("conclusion"));
        }
        if (updates.containsKey("density")) {
            BigDecimal density = validator.validateDensity(new BigDecimal(updates.get("density")));
            labBook.setDensity(density);
        }
        if (updates.containsKey("seriesId")) {
            Series series = seriesService.getSeries(Long.valueOf(updates.get("seriesId")));
            labBook.setSeries(series);
        }
        labBook.setUpdateDate(LocalDateTime.now());
        labBook.setStatus(Status.MODIFIED.name());

        return mapper.mapToExtDto(repository.save(labBook));
    }

    public void delete(Long id) {
        LabBook labBook = getLabBook(id);
        validator.validateDeleted(labBook);
        labBook.setStatus(Status.DELETED.name());

        repository.save(labBook);
    }

    public LabBook getLabBook(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.LAB_NOT_FOUND, id.toString()));
    }
}
