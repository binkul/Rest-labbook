package com.lab.labbook.mapper;

import com.lab.labbook.entity.Supplier;
import com.lab.labbook.entity.dto.SupplierDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierMapper {

    public Supplier mapTuSupplier(SupplierDto supplierDto) {
        return new Supplier(
                supplierDto.getId(),
                supplierDto.getName(),
                supplierDto.getShortName(),
                supplierDto.getAddress(),
                supplierDto.getPhones(),
                supplierDto.isProducer(),
                supplierDto.getComments(),
                new ArrayList<>()
        );
    }

    public SupplierDto mapToDto(Supplier supplier) {
        return new SupplierDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getShortName(),
                supplier.getAddress(),
                supplier.getPhones(),
                supplier.isProducer(),
                supplier.getComments()
        );
    }

    public List<SupplierDto> mapToDtoList(List<Supplier> suppliers) {
        return suppliers.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
