package com.vargas.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosEpisodio(
        @JsonAlias("Title")String titulo,

        @JsonAlias("Episode")Integer numeroDeEpisodio,

        @JsonAlias("imdbRating") String evaluacion,

        @JsonAlias("Released") String fechaDeLanzamiento
) {
}
