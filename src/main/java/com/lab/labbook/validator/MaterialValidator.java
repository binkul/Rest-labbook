package com.lab.labbook.validator;

import com.lab.labbook.entity.Material;
import com.lab.labbook.entity.dto.MaterialDto;
import com.lab.labbook.exception.EntityAlreadyExistsException;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.mapper.MaterialMapper;
import com.lab.labbook.repository.MaterialRepository;
import com.lab.labbook.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class MaterialValidator {

    private final MaterialRepository repository;
    private final MaterialMapper mapper;
    private final CurrencyRateService currencyService;

    public Material validateMaterial(MaterialDto materialDto) {
        materialDto.setPrice(validatePrice(materialDto.getPrice()));
        materialDto.setVoc(validateVoc(materialDto.getVoc()));
        currencyService.getRate(materialDto.getCurrencyId());

        return mapper.mapToMaterial(materialDto);
    }

    public void validateName(String name) {
        if (name == null || name.length()==0) {
            throw new EntityNotFoundException(ExceptionType.EMPTY_NAME, "");
        } else if (repository.existsByName(name)) {
            throw new EntityAlreadyExistsException(ExceptionType.MATERIAL_FOUND, name);
        }
    }

    private BigDecimal validatePrice(BigDecimal price) {
        BigDecimal result = price;
        if (price == null) {
            result = BigDecimal.ZERO;
        }
        return result;
    }

    private BigDecimal validateVoc(BigDecimal voc) {
        BigDecimal result = voc;
        if (voc == null) {
            result = BigDecimal.ZERO;
        }
        return result;
    }

    public void validateNameChange(String oldName, String newName) {
        if (!oldName.equals(newName)) {
            throw new ForbiddenOperationException(ExceptionType.MATERIAL_NAME_CHANGE, newName);
        }
    }
}
