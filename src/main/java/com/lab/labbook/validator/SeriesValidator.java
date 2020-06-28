package com.lab.labbook.validator;

import com.lab.labbook.config.DataConfig;
import com.lab.labbook.entity.Series;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SeriesValidator {

    private final SeriesRepository repository;
    private final DataConfig dataConfig;

    public void validateSeries(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionType.SERIES_ID_NOT_FOUND, id.toString());
        }
    }

    public void validateDefault(Series series) {
        if (series.getTitle().equals(dataConfig.getDefaultUnbound())) {
            throw new ForbiddenOperationException(ExceptionType.USER_DEFAULT_CHANGE, "");
        }
    }
}
