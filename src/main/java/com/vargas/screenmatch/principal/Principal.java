package com.vargas.screenmatch.principal;

import com.vargas.screenmatch.model.DatosEpisodio;
import com.vargas.screenmatch.model.DatosSerie;
import com.vargas.screenmatch.model.DatosTemporadas;
import com.vargas.screenmatch.service.ConsumoAPI;
import com.vargas.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private final String URL_BASE = "http://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=992CF187";

    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu(){
        System.out.println("Porfavor escribe el nombre de la serie que deseas buscar");
        //busca los datos generales de la series
        var nombreSerie = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //busca los datos de todas las temporadas
        //Lista de temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY) ;
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);

        }
        //temporadas.forEach(System.out::println);

        //Mostrar solo el titulo de los episodios para las temporadas
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//
//            }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        }
    }


