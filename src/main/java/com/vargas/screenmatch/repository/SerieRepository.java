package com.vargas.screenmatch.repository;

import com.vargas.screenmatch.model.Categoria;
import com.vargas.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

    // Nueva consulta: Busca series con <= X temporadas Y >= Y evaluación
    List<Serie> findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(
            int totalDeTemporadas,
            double evaluacion
    );

}
