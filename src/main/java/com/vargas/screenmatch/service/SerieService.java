package com.vargas.screenmatch.service;

import com.vargas.screenmatch.dto.EpisodioDTO;
import com.vargas.screenmatch.dto.SerieDTO;
import com.vargas.screenmatch.model.Categoria;
import com.vargas.screenmatch.model.Serie;
import com.vargas.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obtenerTodasLasSeries(){
        return convierteDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    public List<SerieDTO> convierteDatos(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(
                        s.getId(),
                        s.getTitulo(),
                        s.getTotalDeTemporadas(),
                        s.getEvaluacion(),
                        (s.getPoster() == null || s.getPoster().equals("N/A")) ?
                                null :
                                s.getPoster(),
                        s.getGenero().toString(),
                        s.getActores(),
                        s.getSinopsis()
                ))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorCategoria(String nombreGenero) {
        Categoria categoria1 = Categoria.fromEspanol(nombreGenero);
        return convierteDatos(repository.findByGenero(categoria1));
    }

    public SerieDTO obtenerPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getPoster(),
                    s.getGenero().toString(), s.getActores(), s.getSinopsis());
        }
        return null;
    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream().map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(),
                    e.getNumeroDeEpisodio())).collect(Collectors.toList());
        }
        return null;

        }

    public List<EpisodioDTO> obteberTemporadaPorNumero(Long id, Long numeroTemporada) {
        return repository.obtenerTemporadasPorNumero(id, numeroTemporada).stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getTitulo(),
                        e.getNumeroDeEpisodio())).collect(Collectors.toList());
    }
}
