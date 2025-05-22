package com.vargas.screenmatch;

import com.vargas.screenmatch.model.DatosEpisodio;
import com.vargas.screenmatch.model.DatosSerie;
import com.vargas.screenmatch.model.DatosTemporadas;
import com.vargas.screenmatch.service.ConsumoAPI;
import com.vargas.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        var consumoAPI =new ConsumoAPI();
        var json = consumoAPI.obtenerDatos("http://www.omdbapi.com/?apikey=992CF187&t=game+of+thrones");
        System.out.println(json);
        ConvierteDatos conversor = new ConvierteDatos();
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        json = consumoAPI.obtenerDatos("http://www.omdbapi.com/?apikey=992CF187&t=game+of+thrones&Season=1&Episode=1");
        DatosEpisodio episodio = conversor.obtenerDatos(json, DatosEpisodio.class);
        System.out.println(episodio);

        //Lista de temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <datos.totalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos("http://www.omdbapi.com/?apikey=992CF187&t=game+of+thrones&Season="+i);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);

        }
        temporadas.forEach(System.out::println);
    }
}
