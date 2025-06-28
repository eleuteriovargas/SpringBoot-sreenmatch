package com.vargas.screenmatch.controller;

import com.vargas.screenmatch.dto.EpisodioDTO;
import com.vargas.screenmatch.dto.SerieDTO;
import com.vargas.screenmatch.repository.SerieRepository;
import com.vargas.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5500")
@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService servicio;

    @Autowired
    private SerieRepository repository;

    @Autowired
    private RestTemplate restTemplate; // Inyecta RestTemplate

    @GetMapping()
    public List<SerieDTO> obtenerTodasLasSeries(){
        return servicio.obtenerTodasLasSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obternerTop5(){
        return servicio.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return servicio.obtenerLanzamientosMasRecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO obtenerPorId(@PathVariable Long id){
        return servicio.obtenerPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodasLasTemporadas(@PathVariable Long id){
        return servicio.obtenerTodasLasTemporadas(id);

    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obtenerTemporadaPorNumero(@PathVariable Long id,
                                                      @PathVariable Long numeroTemporada){
        return servicio.obteberTemporadaPorNumero(id,numeroTemporada);
    }

    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriesPorCategoria(@PathVariable String nombreGenero) {
        return servicio.obtenerSeriesPorCategoria(nombreGenero);
    }

}