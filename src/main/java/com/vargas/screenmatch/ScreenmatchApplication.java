package com.vargas.screenmatch;

import com.vargas.screenmatch.model.DatosEpisodio;
import com.vargas.screenmatch.model.DatosSerie;
import com.vargas.screenmatch.model.DatosTemporadas;
import com.vargas.screenmatch.principal.Principal;
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

        Principal principal = new Principal();
        principal.muestraElMenu();

    }


}
