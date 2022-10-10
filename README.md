# Guia basica

### Pre requisitos para levantar el proyecto.
* [Apache Maven](https://maven.apache.org)
* [Java JDK 11](https://www.oracle.com/es/java/technologies/javase/jdk11-archive-downloads.html)
  (Recuerden setear el JAVA_HOME en el sistema porque maven lo necesita.)
  
### Para probar
* Ejecutar proyecto: mvn spring-boot:run
* Acceder a swagger para lanzar peticiones: http://localhost:8080/swagger-ui.html


### Comandos
* Compilar: mvn clean install
* Ejecutar proyecto: mvn spring-boot:run
* Ejecutar reporte de TEST en jacoco: mvn test


### Webs del proyecto
* H2 database: http://localhost:8080/h2-ui (usuario "test" y password campo vacio)
* swagger UI: http://localhost:8080/swagger-ui.html
* swagger api doc: http://localhost:8080/v2/api-docs



### Sin Terminar
* Faltaria agregar mas test a los border case de la logica de negocio
* El endpoint de obtener dispositivos no funciona por un problema en la consulta a nivel repository.


### Cosas que haria para salir a produccion
* Montar en docker el servicio para facil despliegue con herramientas como CloudFormation
* Usar una base de datos como PostgresSQL, configurandola en los profiles de properties para diferentes accesos.
* Usar un sistema de versiones para la DB como liquibase para llevar un control de cambios.
* Cambiar la base H2 a scope "test" para que se siga usando como test.
* Deshabilitar swagger fuera de los entornos de DEV.
* Agregar un header de "OperationId" para que se pueda rastrear mas facil los request en el flow.

### Consideraciones
* Use SQL por practicidad con respecto a la implementacion pero si se posee AWS como proveedor cloud hubiera usado
DynamoDB por la velocidad de respuesta en cuanto a la gran cantidad de datos y manejo "schemeless" de la base de datos.