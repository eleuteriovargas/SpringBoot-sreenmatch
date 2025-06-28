ScreenMatch - Plataforma de Series y Películas 🎬

ScreenMatch es una aplicación web desarrollada con Spring Boot (Backend) y HTML/CSS/JavaScript (Frontend) que permite explorar información sobre series de televisión, incluyendo detalles de temporadas, episodios, actores y más.
📌 Características Principales

✅ Catálogo de series
✅ Búsqueda por categorías (Acción, Drama, Comedia, etc.)
✅ Detalles de episodios y temporadas
✅ Top 5 series mejor valoradas
✅ Lanzamientos más recientes
✅ Diseño responsive
🛠 Tecnologías Utilizadas
Backend (API REST)

    Java 17

    Spring Boot 3

    Spring Data JPA

    H2 Database / MySQL (según configuración)

    Maven

Frontend

    HTML5

    CSS3

    JavaScript (ES6+)

    Omdb API (para consumo de endpoints)


Ejecutar el Backend (Spring Boot)
bash

    cd backend
    mvn spring-boot:run

        La API estará disponible en http://localhost:8080

    Ejecutar el Frontend

        Abrir frontend/index.html con Live Server (VS Code) o cualquier servidor web estático.

        La app estará disponible en http://localhost:5500


🔍 Endpoints de la API
Método	Endpoint	Descripción
GET	/series	Obtiene todas las series
GET	/series/top5	Top 5 mejor evaluadas
GET	/series/{id}	Detalles de una serie
GET	/series/{id}/temporadas/todas	Temporadas y episodios
GET	/series/categoria/{genero}	Filtra por categoría