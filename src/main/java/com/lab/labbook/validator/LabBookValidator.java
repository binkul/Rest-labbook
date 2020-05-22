package com.lab.labbook.validator;

import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Status;
import com.lab.labbook.entity.dto.LabBookDto;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.mapper.LabBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class LabBookValidator {

    private final UserValidator userValidator;
    private final SeriesValidator seriesValidator;
    private final LabBookMapper mapper;

    public LabBook validateLabBook(LabBookDto labBookDto) {
        validateTitle(labBookDto.getTitle());
        userValidator.validateUser(labBookDto.getUserId());
        seriesValidator.validateSeries(labBookDto.getSeriesId());
        labBookDto.setDensity(validateDensity(labBookDto.getDensity()));

        return mapper.mapToLabBook(labBookDto);
    }

    public void validateDeleted(LabBook labBook) {
        if (labBook.getStatus().equals(Status.DELETED.name())) {
            throw new ForbiddenOperationException(ExceptionType.LAB_IS_DELETED, "");
        }
    }

    public BigDecimal validateDensity(BigDecimal density) {
        BigDecimal result = density;
        if (density == null || density.compareTo(BigDecimal.ZERO) == 0) {
            result = BigDecimal.ONE;
        }
        return result;
     }

    private void validateTitle(String title) {
        if (title == null || title.length() == 0) {
            throw new ForbiddenOperationException(ExceptionType.LAB_TITLE_EMPTY, "");
        }
    }
}
