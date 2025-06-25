# Proyecto JPA: Consultas y Manejo de Datos

Este proyecto es parte de un curso o aprendizaje autodidacta sobre **Java Persistence API (JPA)**, donde exploramos diferentes formas de realizar consultas a bases de datos utilizando JPA, SQL y JPQL.

En este proyecto, cubrimos los siguientes temas:

### Tipos de consultas en JPA
- **Consultas derivadas**: Métodos que se generan automáticamente a partir del nombre del método en el repositorio.
- **Consultas nativas (`nativeQuery`)**: Consultas SQL puras para casos específicos.
- **JPQL (Java Persistence Query Language)**: Lenguaje de consulta orientado a objetos proporcionado por JPA.

### Métodos personalizados y legibles
- Uso de `@Query` para definir consultas JPQL personalizadas y mejorar la legibilidad del código.
- Ejemplo:
  ```java
  @Query("SELECT s FROM Serie s WHERE s.titulo LIKE %:titulo%")
  List<Serie> buscarPorTitulo(String titulo);

  📊 Expresiones SQL útiles

    Uso de LIKE para búsquedas parciales.

    Ordenamiento con ORDER BY.

    Limitación de resultados con LIMIT (o equivalente en JPQL).

🔗 Recuperación de información relacionada

    Consultas con JOIN para obtener datos de entidades relacionadas (por ejemplo, episodios de una serie).

    Ejemplo:
    java

    @Query("SELECT e FROM Episodio e JOIN e.serie s WHERE s.id = :serieId")
    List<Episodio> obtenerEpisodiosPorSerie(Long serieId);

📅 Manejo de fechas en SQL vs Java
    Comparación entre el manejo de fechas en SQL (como la función YEAR) y Java (con java.time).

    Ejemplo de consulta con fecha:
    java
    @Query("SELECT s FROM Serie s WHERE YEAR(s.fechaEstreno) = :anio")
    List<Serie> buscarPorAnioDeLanzamiento(int anio);

🛠 Tecnologías utilizadas

    Java (con Spring Boot o Jakarta EE, dependiendo del proyecto).

    JPA (Hibernate u otra implementación).

    Base de datos relacional (MySQL, PostgreSQL, H2, etc.).

🚀 Cómo ejecutar el proyecto

    Clona el repositorio:
    bash

git clone [tu-repositorio]

Configura la base de datos en application.properties.

Ejecuta la aplicación con:
bash

    ./mvnw spring-boot:run  # Si usas Maven

¡Gracias por visitar mi repositorio! Si tienes sugerencias o mejoras, ¡no dudes en hacer un PR! 😊
text

