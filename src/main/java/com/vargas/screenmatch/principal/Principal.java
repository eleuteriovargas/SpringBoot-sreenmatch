package com.vargas.screenmatch.principal;

import com.vargas.screenmatch.model.DatosEpisodio;
import com.vargas.screenmatch.model.DatosSerie;
import com.vargas.screenmatch.model.DatosTemporadas;
import com.vargas.screenmatch.model.Episodio;
import com.vargas.screenmatch.service.ConsumoAPI;
import com.vargas.screenmatch.service.ConvierteDatos;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir todas las imformaciones

        List <DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t->t.episodios()
                        .stream()).collect(Collectors.toList());



//        //  Top 5 episodios
//        datosEpisodios.stream()
//                .filter(e ->!e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro (N/A)" + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("segundo ordenación (M>m)" + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Tercer filtro Mayúscula (m>m)" + e))
//                .limit(5)
//                .forEach(System.out::println);





        //Convirtiendo los datos a una lista del tipo episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t->t.episodios().stream()
                .map(d ->new Episodio(t.numero(),d)))
                .collect(Collectors.toList());

//        episodios.forEach(System.out::println);

        //Busqueda de episodios a partir de x año
        //System.out.println("a apartir de que año deaseas ver los episodios?");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e ->e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada " + e.getTemporada() +
//                                "Episodio " + e.getTitulo() +
//                                "fecha de Lanzamiento " + e.getFechaDeLanzamiento().format(dtf)
//                ));

//        //Busca episodios por un pedazo de título
//        System.out.println("Por favor escriba el título del episodio que desea ver");
//        var pedazoTitulo = teclado.nextLine();
//        Optional<Episodio> episodiobuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//
//        if(episodiobuscado.isPresent()){
//            System.out.println(" Episodio encontrado");
//            System.out.println("Los datos son: " + episodiobuscado.get());
//        }else {
//            System.out.println("Episodio no encontrado");
//        }

        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluaciones() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluaciones)));
        System.out.println(evaluacionesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluaciones() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluaciones));
        System.out.println("Media de las evaluaciones; " + est.getAverage());
        System.out.println("Episodio mejor evaluado; " + est.getMax());
        System.out.println("Episodio Peor evaluado; " + est.getMin());


    }
}


