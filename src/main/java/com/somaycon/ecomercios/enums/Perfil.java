package com.somaycon.ecomercios.enums;

public enum Perfil {
    ADMIN("ROLE_ADMIN"),
    CLIENTE("ROLE_CLIENTE");

    private final String perfil;

    Perfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil(){
        return perfil;
    }
}
