package com.lab.labbook.service;

import com.lab.labbook.entity.Supplier;
import com.lab.labbook.entity.dto.SupplierDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.mapper.SupplierMapper;
import com.lab.labbook.repository.MaterialRepository;
import com.lab.labbook.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SupplierService {

    private final SupplierRepository repository;
    private final SupplierMapper mapper;
    private final MaterialRepository materialRepository;

    public List<SupplierDto> getAll() {
        return mapper.mapToDtoList(repository.findAllByOrderByNameAsc());
    }

    public SupplierDto getById(Long id) {
        return mapper.mapToDto(getSupplier(id));
    }

    public List<SupplierDto> getByName(String name) {
        return mapper.mapToDtoList(repository.findAllByNameOrderByName(name));
    }

    public void create(SupplierDto supplierDto) {
        repository.save(mapper.mapTuSupplier(supplierDto));
    }

    public SupplierDto update(SupplierDto supplierDto) {
        Supplier supplier = mapper.mapTuSupplier(supplierDto);
        return mapper.mapToDto(repository.save(supplier));
    }

    public void delete(Long id) {
        Supplier supplier = getSupplier(id);
        if (materialRepository.existsBySupplier(supplier)) {
            throw new ForbiddenOperationException(ExceptionType.SUPPLIER_IN_USE, supplier.getShortName());
        } else {
            repository.deleteById(id);
        }
    }

    public Supplier getSupplier(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.SUPPLIER_NOT_FOUND, id.toString()));
    }
}
