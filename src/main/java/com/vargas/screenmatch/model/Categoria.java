package com.vargas.screenmatch.model;

import static java.awt.SystemColor.text;

public enum Categoria {

    ACCION("Action"),

    ROMANCE("Romance"),

    COMEDIA("Comedy"),

    DRAMA("Drama"),

    CRIMEN("Crime"),

    AVENTURA("Adventure");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalStateException("Ninguna categoria encontrada: " + text);
    }

    public String getCategoriaOmdb() {
        return categoriaOmdb;
    }

    public void setCategoriaOmdb(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }
}
