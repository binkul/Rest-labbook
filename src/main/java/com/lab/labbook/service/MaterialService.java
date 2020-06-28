package com.lab.labbook.service;

import com.lab.labbook.entity.Material;
import com.lab.labbook.entity.clp.ClpSymbol;
import com.lab.labbook.entity.clp.Ghs;
import com.lab.labbook.entity.clp.GhsDto;
import com.lab.labbook.entity.dto.MaterialDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.mapper.MaterialMapper;
import com.lab.labbook.repository.ClpSymbolRepository;
import com.lab.labbook.repository.IngredientRepository;
import com.lab.labbook.repository.MaterialRepository;
import com.lab.labbook.validator.MaterialValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@EnableAspectJAutoProxy
@Service
public class MaterialService {

    private final MaterialMapper mapper;
    private final MaterialRepository repository;
    private final ClpSymbolRepository clpRepository;
    private final MaterialValidator validator;
    private final IngredientRepository ingredientRepository;

    public List<MaterialDto> getAll() {
        return mapper.mapToListDto(repository.findAllByOrderByNameAsc());
    }

    public MaterialDto getById(Long id) {
        return mapper.mapToDto(getMaterial(id));
    }

    public List<MaterialDto> getByName(String name) {
        return mapper.mapToListDto(repository.findAllByNameOrderByName(name));
    }

    public void create(MaterialDto materialDto) {
        Material material = validator.validateMaterial(materialDto);
        validator.validateName(materialDto.getName());
        repository.save(material);
    }

    public MaterialDto update(MaterialDto materialDto) {
        Material material = validator.validateMaterial(materialDto);
        validator.validateNameChange(material.getName(), materialDto.getName());

        return mapper.mapToDto(repository.save(material));
    }

    public void updateGhs(Long id, GhsDto ghsDto) {
        Material material = getMaterial(id);
        if (ghsDto.isState()) {
            String symbol = Ghs.getGhs(ghsDto.getSymbol().toUpperCase()).name();
            ClpSymbol clpSymbol = new ClpSymbol(symbol, material);
            clpRepository.save(clpSymbol);
        } else {
            material.getClpSymbols().stream()
                    .filter(i -> i.getSymbol().equals(ghsDto.getSymbol().toUpperCase()))
                    .findFirst()
                    .ifPresent(i -> {
                        material.getClpSymbols().remove(i);
                        clpRepository.delete(i);
                    });
        }
    }

    public void delete(Long id) {
        Material material = getMaterial(id);
        if (ingredientRepository.existsByMaterial(material)) {
            throw new ForbiddenOperationException(ExceptionType.MATERIAL_IN_USE, material.getName());
        } else {
            repository.deleteById(id);
        }
    }

    public Material getMaterial(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.MATERIAL_NOT_FOUND, id.toString()));
    }
}
