package com.example.backendcreditas.model.customer;

public enum Location {
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernambuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private final String name;

    Location(String name) {
        this.name = name;
    }

    public String getNome() {
        return name;
    }

    public static Location fromSigla(String sigla) {
        for (Location location : Location.values()) {
            if (location.name().equalsIgnoreCase(sigla)) {
                return location;
            }
        }
        throw new IllegalArgumentException("Estado inválido: " + sigla);
    }
}
