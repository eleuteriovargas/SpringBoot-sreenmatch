package com.vargas.screenmatch.model;

import java.text.Normalizer;

import static java.awt.SystemColor.text;

public enum Categoria {

    ACCION("Action", "Acción"),

    ROMANCE("Romance", "Romance"),

    COMEDIA("Comedy", "Comedia"),

    DRAMA("Drama", "Drama"),

    CRIMEN("Crime", "Crimen"),

    AVENTURA("Adventure", "Aventura");

    private String categoriaOmdb;

    private String categoriaEspanol;

    Categoria(String categoriaOmdb, String categoriaEspanol) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalStateException("Ninguna categoria encontrada: " + text);
    }

//    public static Categoria fromEspanol(String text) {
//        for (Categoria categoria : Categoria.values()){
//            if (categoria.categoriaEspanol.equalsIgnoreCase(text)){
//                return categoria;
//            }
//        }
//        throw new IllegalStateException("Ninguna categoria encontrada: " + text);
//    }


    public static Categoria fromEspanol(String text) {
        String normalizedInput = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase();

        for (Categoria categoria : Categoria.values()) {
            String normalizedCategoria = Normalizer.normalize(categoria.getCategoriaEspanol(), Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .toLowerCase();

            if (normalizedCategoria.equals(normalizedInput)) {
                return categoria;
            }
        }
        throw new IllegalStateException("Ninguna categoria encontrada: " + text);
    }
    public String getCategoriaOmdb() {
        return categoriaOmdb;
    }

    public String getCategoriaEspanol() {
        return categoriaEspanol;
    }

}
