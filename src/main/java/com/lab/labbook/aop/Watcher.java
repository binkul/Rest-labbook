package com.lab.labbook.aop;

import com.lab.labbook.entity.Log;
import com.lab.labbook.entity.LogLevel;
import com.lab.labbook.entity.dto.LabBookDto;
import com.lab.labbook.entity.dto.MaterialDto;
import com.lab.labbook.entity.dto.SeriesDto;
import com.lab.labbook.entity.dto.UserDto;
import com.lab.labbook.service.LogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class Watcher {

    private final LogService logService;

    @AfterReturning("execution(* com.lab.labbook.service.LabBookService.create(..)) && args(labBookDto)")
    public void logAddLabBook(LabBookDto labBookDto) {
        Log log = new Log(LogLevel.INFO.name(), LogData.CREATE_LAB, "Title: " + labBookDto.getTitle());
        logService.save(log);
        logService.sendLog(log);
    }

    @AfterReturning("execution(* com.lab.labbook.service.LabBookService.update(..)) && args(labBookDto)")
    public void logUpdateLabBook(LabBookDto labBookDto) {
        Log log = new Log(LogLevel.INFO.name(), LogData.UPDATE_LAB + labBookDto.getId(), "Title: " + labBookDto.getTitle());
        logService.save(log);
    }

    @AfterReturning("execution(* com.lab.labbook.service.LabBookService.delete(..)) && args(id)")
    public void logDeleteLabBook(Long id) {
        Log log = new Log(LogLevel.WARN.name(), LogData.DELETE_LAB + id, "Laboratory work was deleted");
        logService.save(log);
    }

    @AfterReturning("execution(* com.lab.labbook.service.MaterialService.create(..)) && args(materialDto)")
    public void logAddMaterial(MaterialDto materialDto) {
        Log log = new Log(LogLevel.INFO.name(), LogData.CREATED_MAT, "Name: " + materialDto.getName());
        logService.save(log);
    }

    @AfterReturning("execution(* com.lab.labbook.service.MaterialService.update(..)) && args(materialDto)")
    public void logUpdateMaterial(MaterialDto materialDto) {
        Log log = new Log(LogLevel.INFO.name(), LogData.UPDATED_MAT + materialDto.getId(), "Name: " + materialDto.getName());
        logService.save(log);
    }

    @AfterReturning("execution(* com.lab.labbook.service.MaterialService.delete(..)) && args(id)")
    public void logDeleteMaterial(Long id) {
        Log log = new Log(LogLevel.WARN.name(), LogData.DELETED_MAT + id, "Material was deleted.");
        logService.save(log);
    }

    @AfterReturning("execution(* com.lab.labbook.service.SeriesService.create(..)) && args(seriesDto)")
    public void logAddSeries(SeriesDto seriesDto) {
        Log log = new Log(LogLevel.INFO.name(), LogData.CREATED_SER, "Title: " + seriesDto.getTitle());
        logService.save(log);
    }

    @AfterReturning("execution(* com.lab.labbook.service.SeriesService.update(..)) && args(seriesDto)")
    public void logUpdateSeries(SeriesDto seriesDto) {
        Log log = new Log(LogLevel.INFO.name(), LogData.UPDATED_SER + seriesDto.getId(), "Title: " + seriesDto.getTitle());
        logService.save(log);
    }

    @AfterReturning("execution(* com.lab.labbook.service.MaterialService.delete(..)) && args(id)")
    public void logDeleteSeries(Long id) {
        Log log = new Log(LogLevel.WARN.name(), LogData.DELETED_SER + id, "Series was deleted.");
        logService.save(log);
    }

    @AfterReturning("execution(* com.lab.labbook.facade.UserFacade.fetchCreate(..)) && args(userDto)")
    public void logAddUser(UserDto userDto) {
        Log log = new Log(LogLevel.INFO.name(), LogData.CREATED_USER, "Person: " + userDto.getName() + " " + userDto.getLastName());
        logService.save(log);
        logService.sendLog(log);
    }

    @AfterReturning("execution(* com.lab.labbook.facade.UserFacade.fetchUpdate(..)) && args(userDto)")
    public void logUpdateUser(UserDto userDto) {
        Log log = new Log(LogLevel.INFO.name(), LogData.UPDATED_USER + userDto.getLogin(), "Person: "  + userDto.getName() + " " + userDto.getLastName());
        logService.save(log);
    }

    @AfterReturning("execution(* com.lab.labbook.facade.UserFacade.fetchDelete(..)) && args(id)")
    public void logDeleteUser(Long id) {
        Log log = new Log(LogLevel.WARN.name(), LogData.DELETED_USER + id, "User was deleted.");
        logService.save(log);
    }
}
