package com.lab.labbook.entity.clp;

import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Ghs {
    GHS01("Explosion"),
    GHS02("Flammable"),
    GHS03("Oxidants"),
    GHS04("Compressed gas"),
    GHS05("Corrosive"),
    GHS06("Toxic"),
    GHS07("Harmful"),
    GHS08("Mutagen"),
    GHS09("Toxic to environment");

    private String description;

    Ghs(String description) {
        this.description = description;
    }

    public static boolean exist(String symbol) {
        return Stream.of(Ghs.values())
                .map(Enum::name)
                .anyMatch(i -> i.equals(symbol));
    }

    public static Ghs getGhs(String symbol) {
        return Stream.of(Ghs.values())
                .filter(i -> i.name().equals(symbol))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.SYMBOL_NOT_FOUND, symbol));
    }
}
