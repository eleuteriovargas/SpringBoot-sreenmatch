package com.vargas.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
                        @JsonAlias("Title") String titulo,

                         @JsonAlias("totalSeasons") Integer totalDeTemporadas,

                        @JsonAlias("imdbRating") String evaluaciones,

                        @JsonAlias("Genre") String genero,

                        @JsonAlias("Plot") String sinopsis,

                        @JsonAlias("Poster") String poster,

                        @JsonAlias("Actors") String actores,

                        @JsonAlias("Action") String accion
                        )

{
}
