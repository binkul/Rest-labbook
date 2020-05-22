package com.lab.labbook.mapper;

import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Series;
import com.lab.labbook.entity.User;
import com.lab.labbook.entity.dto.LabBookDto;
import com.lab.labbook.entity.extended.LabBookExtDto;
import com.lab.labbook.service.SeriesService;
import com.lab.labbook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LabBookMapper {

    private final UserService userService;
    private final SeriesService seriesService;

    public LabBook mapToLabBook(LabBookDto labBookDto) {
        User user = userService.getById(labBookDto.getUserId());
        Series series = seriesService.getSeries(labBookDto.getSeriesId());
        return new LabBook.LabBuilder()
                .id(labBookDto.getId())
                .title(labBookDto.getTitle())
                .description(labBookDto.getDescription())
                .conclusion(labBookDto.getConclusion())
                .density(labBookDto.getDensity())
                .user(user)
                .series(series)
                .build();
    }

    public LabBookExtDto mapToExtDto(LabBook labBook) {
        return new LabBookExtDto.LabExtBuilder()
                .id(labBook.getId())
                .title(labBook.getTitle())
                .description(labBook.getDescription())
                .conclusion(labBook.getConclusion())
                .density(labBook.getDensity())
                .updateDate(labBook.getCreationDate())
                .creationDate(labBook.getUpdateDate())
                .status(labBook.getStatus())
                .userId(labBook.getUser().getId())
                .seriesId(labBook.getSeries().getId())
                .build();
    }

    public List<LabBookExtDto> mapToExtListDto(List<LabBook> list) {
        return list.stream()
                .map(this::mapToExtDto)
                .collect(Collectors.toList());
    }
}
