package com.lab.labbook.validator;

import com.lab.labbook.config.DataConfig;
import com.lab.labbook.entity.Series;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.repository.SeriesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SeriesValidatorTestSuite {

    @InjectMocks
    private SeriesValidator validator;

    @Mock
    private SeriesRepository repository;

    @Mock
    private DataConfig dataConfig;

    @Test(expected = EntityNotFoundException.class)
    public void testValidateSeries() {
        // Given

        // When Then
        when(repository.existsById(1L)).thenReturn(false);
        validator.validateSeries(1L);
    }

    @Test(expected = ForbiddenOperationException.class)
    public void testValidateDefault() {
        // Given
        Series series = new Series(1L, "default", new ArrayList<>());

        // When Then
        when(dataConfig.getDefaultUnbound()).thenReturn("default");
        validator.validateDefault(series);
    }
}
