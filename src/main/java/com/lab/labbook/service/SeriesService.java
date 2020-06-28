package com.lab.labbook.service;

import com.lab.labbook.config.DataConfig;
import com.lab.labbook.entity.Series;
import com.lab.labbook.entity.dto.SeriesDto;
import com.lab.labbook.exception.EntityAlreadyExistsException;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.mapper.SeriesMapper;
import com.lab.labbook.repository.LabBookRepository;
import com.lab.labbook.repository.SeriesRepository;
import com.lab.labbook.validator.SeriesValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SeriesService {

    private final DataConfig dataConfig;
    private final SeriesMapper seriesMapper;
    private final SeriesRepository seriesRepository;
    private final LabBookRepository labBookRepository;
    private final SeriesValidator seriesValidator;

    public List<SeriesDto> getAll() {
        return seriesMapper.mapToListDto(seriesRepository.findAllByOrderByTitleAsc());
    }

    public SeriesDto getById(Long id) {
        Series series = getSeries(id);
        return seriesMapper.mapToDto(series);
    }

    public List<SeriesDto> getByTitle(String searchTitle) {
        return seriesMapper.mapToListDto(seriesRepository.findAllByTitleOrderByTitle(searchTitle));
    }

    public SeriesDto create(SeriesDto seriesDto) {
        if (seriesRepository.existsByTitle(seriesDto.getTitle())) {
            throw new EntityAlreadyExistsException(ExceptionType.SERIES_FOUND, seriesDto.getTitle());
        }
        return seriesMapper.mapToDto(seriesRepository.save(seriesMapper.mapToSeries(seriesDto)));
    }

    public SeriesDto update(SeriesDto seriesDto) {
        getSeries(seriesDto.getId());
        return create(seriesDto);
    }

    public void delete(Long id) {
        Series oldSeries = getSeries(id);
        seriesValidator.validateDefault(oldSeries);
        if (labBookRepository.existsBySeries(oldSeries)) {
            Series newSeries = getDefault();
            labBookRepository.updateSeriesId(oldSeries, newSeries);
        }
        seriesRepository.delete(oldSeries);
    }

    public Series getSeries(Long id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.SERIES_ID_NOT_FOUND, id.toString()));
    }

    private Series getDefault() {
        return seriesRepository.findByTitle(dataConfig.getDefaultUnbound())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.SERIES_ID_NOT_FOUND, dataConfig.getDefaultUnbound()));
    }
}
