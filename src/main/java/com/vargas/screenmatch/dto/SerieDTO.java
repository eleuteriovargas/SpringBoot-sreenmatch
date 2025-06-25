package com.vargas.screenmatch.dto;

import com.vargas.screenmatch.model.Categoria;

public record SerieDTO(
        String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        String genero,
        Categoria poster,
        String actores,
        String sinopsis) {
}
