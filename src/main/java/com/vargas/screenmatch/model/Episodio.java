package com.vargas.screenmatch.model;

import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name="episodios")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private  Integer temporada;

    private String titulo;

    private Integer numeroDeEpisodio;

    private Double evaluaciones;

    private LocalDate fechaDeLanzamiento;

    @ManyToOne
    private Serie serie;


    public Episodio(){

    }

    public Episodio(Integer numero, DatosEpisodio d) {
        this.temporada = numero;
        this.titulo = d.titulo();
        this.numeroDeEpisodio = d.numeroDeEpisodio();

        try {
            this.evaluaciones = Double.valueOf(d.evaluacion());
        } catch (NumberFormatException e) {
            this.evaluaciones = 0.0;
        }

        try {
            this.fechaDeLanzamiento = LocalDate.parse(d.fechaDeLanzamiento());
        } catch (DateTimeParseException e) {
            this.fechaDeLanzamiento = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroDeEpisodio() {
        return numeroDeEpisodio;
    }

    public void setNumeroDeEpisodio(Integer numeroDeEpisodio) {
        this.numeroDeEpisodio = numeroDeEpisodio;
    }

    public Double getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(Double evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public LocalDate getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public void setFechaDeLanzamiento(LocalDate fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroDeEpisodio=" + numeroDeEpisodio +
                ", evalaciones=" + evaluaciones +
                ", fechaDeLanzamiento=" + fechaDeLanzamiento;
    }
}
