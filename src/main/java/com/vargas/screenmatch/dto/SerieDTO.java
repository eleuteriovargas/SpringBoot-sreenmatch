package com.vargas.screenmatch.dto;

import com.vargas.screenmatch.model.Categoria;

public record SerieDTO(Long id,
        String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        String poster,
        String genero,

        //Categoria poster,
        String actores,
        String sinopsis
        )
{
}
