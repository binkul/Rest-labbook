package com.lab.labbook.mapper;

import com.lab.labbook.entity.Series;
import com.lab.labbook.entity.dto.SeriesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class SeriesMapper {

    public Series mapToSeries(SeriesDto seriesDto) {
        return new Series(seriesDto.getId(), seriesDto.getTitle(), new ArrayList<>());
    }

    public SeriesDto mapToDto(Series series) {
        return new SeriesDto(series.getId(), series.getTitle());
    }

    public List<SeriesDto> mapToListDto(List<Series> series) {
        return series.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
