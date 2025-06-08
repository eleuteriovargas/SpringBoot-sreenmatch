package com.vargas.screenmatch.model;


import com.vargas.screenmatch.service.ConsultaGemini;



import java.util.OptionalDouble;

public class Serie {

    private String titulo;

    private Integer totalDeTemporadas;

    private Double evaluacion;

    private Categoria genero;

   private ConsultaGemini consultaGemini;

    private String sinopsis;

    private String poster;

    private String actores;

    public Serie(DatosSerie datosSerie){
        this.titulo = datosSerie.titulo();
        this.totalDeTemporadas = datosSerie.totalDeTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluaciones())).orElse(0);
        this.poster = datosSerie.poster();
        this.genero = Categoria.fromString(datosSerie.genero().split(",") [0].trim());
        this.actores = datosSerie.actores();
        this.sinopsis = ConsultaGemini.obtenerTraduccion(datosSerie.sinopsis());


    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    @Override
    public String toString() {
        return
                " genero = " + genero +
                ", titulo = '" + titulo + '\'' +
                ", totalDeTemporadas = " + totalDeTemporadas +
                ", evaluacion = " + evaluacion +
                ", poster = '" + poster + '\'' +
                ", actores = '" + actores + '\'' +
                ", sinopsis = " + sinopsis ;
    }
}
