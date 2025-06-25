package com.vargas.screenmatch.principal;

import com.vargas.screenmatch.model.*;
import com.vargas.screenmatch.repository.SerieRepository;
import com.vargas.screenmatch.service.ConsumoAPI;
import com.vargas.screenmatch.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);

    private ConsumoAPI consumoApi = new ConsumoAPI();

    private final String URL_BASE = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=992CF187";//${API_KEY_OMDB}";

    private ConvierteDatos conversor = new ConvierteDatos();

    private List<DatosSerie> datosSeries = new ArrayList<>();
//
    private SerieRepository repository;

    private List<Serie> series = new ArrayList<>();

    private Optional<Serie> seriebuscada;

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void muestraElMenu() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar series por titulo
                    5 - Top 5 mejores series
                    6 - Buscar series por categoria
                    7 - Buscar series por temporadas y evaluación (Filtrar)
                    8 - Buscar episodios por titulo
                    9 - Top 5 epispdios
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPortitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriesPorCategoria();
                    break;
                case 7:
                    buscarSeriesPorTemporadasYEvaluacion();
                    break;
                case 8:
                    buscarEpisodiosPorTitulo();
                    break;
                case 9:
                    buscarTop5Episodios();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

//    private void buscarSerieWeb() {
//        DatosSerie datos = getDatosSerie();
//        Serie serie = new Serie(datos);
//        repository.save(serie);
//        //datosSeries.add(datos);
//        //System.out.println(datos);
//    }

    private void buscarSerieWeb() {
        try {
            System.out.println("Escribe el nombre de la serie que deseas buscar");
            String nombreSerie = teclado.nextLine();

            // 1. Verificar si ya existe en la base de datos (ignorando mayúsculas y acentos)
            Optional<Serie> serieExistente = repository.findByTituloContainsIgnoreCase(nombreSerie);

            if (serieExistente.isPresent()) {
                System.out.println("La serie '" + nombreSerie + "' ya está registrada.");
                System.out.println("Datos de la serie existente: " + serieExistente.get());
                return; // Salir sin guardar duplicados
            }

            // 2. Si no existe, buscarla en la API (OMDb, etc.)
            DatosSerie datos = getDatosSerie(); // Tu método actual para obtener datos de la API
            Serie nuevaSerie = new Serie(datos);

            // 3. Guardar la nueva serie
            repository.save(nuevaSerie);
            System.out.println("Serie guardada exitosamente: " + nuevaSerie.getTitulo());

        } catch (Exception e) {
            System.err.println("Error al procesar la serie: " + e.getMessage());
        }
    }

    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie por la cual quieres ver los episodios");
        var nombreSerie = teclado.nextLine();

        Optional <Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }

            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);
        }
    }

    private void mostrarSeriesBuscadas() {
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriesPortitulo() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        seriebuscada = repository.findByTituloContainsIgnoreCase(nombreSerie);

        if(seriebuscada.isPresent()){
            System.out.println("La serie buscada es: " + seriebuscada.get());
        }else {
            System.out.println("Serie no encontrado");
        }
    }

    private void buscarTop5Series(){
        List<Serie> topSeries = repository.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s ->
                System.out.println("Serie: " + s.getTitulo() + " Evaluacion: " + s.getEvaluacion()));
    }

//    private void buscarSeriesPorCategoria(){
//
//            System.out.println("Escriba el genero/categoria de la serie" +
//                    " que desea buscar");
//            var genero = teclado.nextLine();
//            var categoria = Categoria.fromEspanol(genero);
//            List<Serie> seriesPorCategoria = repository.findByGenero(categoria);
//            System.out.println("La serie de la categoria " + genero);
//            seriesPorCategoria.forEach(System.out::println);
//        }

    private void buscarSeriesPorCategoria() {
        try {
            System.out.println("Escriba el género/categoría de la serie que desea buscar");
            System.out.println("Categorías disponibles: " +
                    Arrays.stream(Categoria.values())
                            .map(Categoria::getCategoriaEspanol) // Usando el getter
                            .collect(Collectors.joining(", ")));

            var genero = teclado.nextLine();
            var categoria = Categoria.fromEspanol(genero);
            List<Serie> seriesPorCategoria = repository.findByGenero(categoria);

            if (seriesPorCategoria.isEmpty()) {
                System.out.println("No se encontraron series para la categoría: " + genero);
            } else {
                System.out.println("Series de la categoría " + genero + ":");
                seriesPorCategoria.forEach(System.out::println);
            }
        } catch (IllegalStateException e) {
            System.out.println("Error: La categoría ingresada no existe. Las categorías disponibles son:");
            Arrays.stream(Categoria.values())
                    .map(Categoria::getCategoriaEspanol) // Usando el getter
                    .forEach(System.out::println);
        }
    }

    private void buscarSeriesPorTemporadasYEvaluacion() {
        try {
            System.out.println("Búsqueda personalizada");
            System.out.println("Ingresa el número MÁXIMO de temporadas:");
            int totalDeTemporadas = teclado.nextInt();

            System.out.println("Ingresa la evaluación MÍNIMA (ej: 8.5):");
            double evaluacion = teclado.nextDouble();
            teclado.nextLine(); // Limpiar el buffer

            // Consulta a la base de datos
//            List<Serie> seriesFiltradas = repository
//                    .findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(
//                            maxTemporadas,
//                            minEvaluacion
//                    );
            //Opcion 7 con Query Nativa
            List<Serie> seriesFiltradas = repository.seriesPorTemporadaYEvaluacion(totalDeTemporadas, evaluacion);

            if (seriesFiltradas.isEmpty()) {
                System.out.println("No hay series con esos criterios.");
            } else {
                System.out.println("Resultados:");
                seriesFiltradas.forEach(serie ->
                        System.out.printf(
                                "- %s (%d temporadas, %.1f)\n",
                                serie.getTitulo(),
                                serie.getTotalDeTemporadas(),
                                serie.getEvaluacion()
                        )
                );
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingresa un número válido.");
            teclado.nextLine(); // Limpiar entrada incorrecta
        }
    }

    private void buscarEpisodiosPorTitulo() {
        System.out.println("Escribe el nombre del episodio que deseas buscar");
        var nombreEpisodio = teclado.nextLine();
        List<Episodio> episodiosEncontardos = repository.episodiosPorNombre(nombreEpisodio);
        episodiosEncontardos.forEach(e ->
                System.out.printf("Serie: %s Temporada %s Episodio %s Evaluacion %s \n",
                        e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroDeEpisodio(), e.getEvaluaciones()));

    }

    private void buscarTop5Episodios() {
        buscarSeriesPortitulo();
        if (seriebuscada.isPresent()){
            Serie serie = seriebuscada.get();
            List<Episodio> topEpisodios = repository.top5Episodios(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Serie: %s - Temporada %s - Episodio %s - Evaluacion %s \n",
                            e.getSerie().getTitulo(), e.getTemporada(), e.getTitulo(), e.getEvaluaciones()));

        }

    }
}
