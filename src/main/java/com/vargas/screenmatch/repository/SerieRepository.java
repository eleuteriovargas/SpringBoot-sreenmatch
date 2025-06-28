package com.vargas.screenmatch.repository;

import com.vargas.screenmatch.model.Categoria;
import com.vargas.screenmatch.model.Episodio;
import com.vargas.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

//     Nueva consulta: Busca series con <= X temporadas Y >= Y evaluación
//    List<Serie> findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(
//            int totalDeTemporadas,
//            double evaluacion
//    );

//    //Query Nativa
//    @Query(value = "select * from series where series.total_de_temporadas <= 6 and series.evaluacion >= 7.5", nativeQuery = true)
//    List<Serie> seriesPorTemporadaYEvaluacion();

    //Java JPQL
    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas <= :totalDeTemporadas and s.evaluacion >= :evaluacion")
    List<Serie> seriesPorTemporadaYEvaluacion(int totalDeTemporadas, Double evaluacion);

    //otros métodos
    @Query("SELECT s FROM Serie s " + "JOIN s.episodios e " + "GROUP BY s " + "ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();


    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);


    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluaciones DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> obtenerTemporadasPorNumero(Long id, Long numeroTemporada);
}
