package com.lab.labbook.client.weather;

public enum Cities {
    WARSZAWA("pl"),
    TORUN("pl"),
    WROCLAW("pl"),
    POZNAN("pl"),
    KRAKOW("pl"),
    OLSZTYN("pl"),
    GDANSK("pl"),
    LODZ("pl"),
    PRAHA("cz"),
    BERLIN("de");

    private String symbol;

    Cities(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
