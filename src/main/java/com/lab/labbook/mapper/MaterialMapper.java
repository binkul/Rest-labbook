package com.lab.labbook.mapper;

import com.lab.labbook.entity.CurrencyRate;
import com.lab.labbook.entity.Material;
import com.lab.labbook.entity.Supplier;
import com.lab.labbook.entity.clp.ClpSymbol;
import com.lab.labbook.entity.clp.Ghs;
import com.lab.labbook.entity.dto.MaterialDto;
import com.lab.labbook.service.CurrencyRateService;
import com.lab.labbook.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MaterialMapper {

    private final CurrencyRateService currencyRateService;
    private final SupplierService supplierService;

    public Material mapToMaterial(MaterialDto materialDto) {
        CurrencyRate currencyRate = currencyRateService.getRate(materialDto.getCurrencyId());
        Supplier supplier = supplierService.getSupplier(materialDto.getSupplierId());

        return new Material(
                materialDto.getId(),
                materialDto.getName(),
                materialDto.getPrice(),
                materialDto.getVoc(),
                currencyRate,
                supplier);
    }

    public MaterialDto mapToDto(Material material) {
        return new MaterialDto(
                material.getId(),
                material.getName(),
                material.getPrice(),
                material.getVoc(),
                material.getCurrencyRate().getId(),
                material.getCurrencyRate().getSymbol(),
                material.getSupplier().getId(),
                getSymbols(material.getClpSymbols()));
    }

    public List<MaterialDto> mapToListDto(List<Material> materials) {
        return materials.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private Map<String, String> getSymbols(List<ClpSymbol> symbols) {
        return symbols.stream()
                .map(ClpSymbol::getSymbol)
                .map(Ghs::getGhs)
                .collect(Collectors.toMap(Enum::name, Ghs::getDescription));
    }
}
