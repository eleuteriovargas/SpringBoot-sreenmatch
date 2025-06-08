package com.vargas.screenmatch.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class ConsultaGemini {


    public static String obtenerTraduccion(String texto) {
        String modelo = "gemini-2.0-flash-lite"; // Puedes cambiar la versión si lo deseas
        String prompt = "Traduce el siguiente texto al español: " + texto;


        Client cliente = new Client.Builder().apiKey("AIzaSyAoq2LPvJ3_4-bXylvY2O01apWB5_5_xE8").build();

        try {
            GenerateContentResponse respuesta = cliente.models.generateContent(
                    modelo,
                    prompt,
                    null // Parámetro para configuraciones adicionales
            );

            if (!respuesta.text().isEmpty()) {
                return respuesta.text();
            }
        } catch (Exception e) {
            System.err.println("Error al llamar a la API de Gemini para traducción: " + e.getMessage());
        }

        return null;

    }

}
