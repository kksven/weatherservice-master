
# Descripción

Tú y tu buena amiga se han propuesto construir una primera versión de un servicio meteorológico en no más de 3 horas.
Para ello consideran exponer un API REST utilizando Spring Boot.
Para ayudarte, ella te ha dejado esta estructura inicial de un proyecto maven, con algunas dependencias importantes.
Ha decidido utilizar Java 11, pero si lo prefieres puedes utilizar Java 8, adaptando el proyecto para ello.

Tras un análisis han considerado que un dato meteorológico presenta las siguientes características:

+ id: ID único de los datos meteorológicos.
+ date: Fecha de registro de datos meteorológicos (formato yyyy-MM-dd).
+ location: Lugar para el que se registraron los datos meteorológicos.
El location en sí es un elemento que consta de los siguientes campos:
  + lat: Latitud de la ubicación (Máximo cuatro decimales).
  + lon: Longitud de la ubicación (Máximo cuatro decimales).
  + city: Nombre de la ciudad.
  + state: Nombre del estado.
+ temperature: Array de 4 valores flotantes (hasta un decimal), que describe la temperatura por hora (en F°) para la ubicación dada.

Tu amiga te deja un ejemplo de un JSON que representa un dato meteorológico:

```json
{
    "id": 37892,
    "date": "2020-09-15",
    "location": {
        "lat": 32.7767,
        "lon": 96.7970,
        "city": "Dallas",
        "state": "Texas"
    },
    "temperature": [
        89.7,
        84.3,
        91.2,
        93.1
    ]
}
```

## Funcionalidades

Como funcionalidades básicas se han propuesto entregar las siguientes:

### GET /weathers

Obtiene todos los datos meteorológicos presentes en el sistema, ordenados descendentemente por ID.

### GET /weathers/{weatherId}

Obtiene un dato meteorológico representado por el id provisto

### GET /weathers?date={date}

Obtiene todos los datos meteorológicos que están asociados con la fecha dada.

### POST /weathers

Permite agregar un dato meteorológico considerando que no se actualiza la información en caso de existir el ID.

### PUT /weathers

Permite actualizar un dato meteorológico. Si no existe un dato meteorológico para el ID presentado, el sistema creará una nueva entrada, retornando la misma en la respuesta.
Si previamente existe un dato meteorológico para el ID presentado, el sistema actualizará el registro y lo retornará en la respuesta.

### DELETE /weathers/{weatherId}

Permite eliminar un registro identificado con el id provisto.

### DELETE /weathers

Permite eliminar todos los registros del sistema.

### GET /weather/report?startDate={startDate}&endDate={endDate}

Obtiene un reporte de los datos meteorológicos registrados entre las fechas startDate y endDate.

Si se provee endDate pero no se provee un startDate, se asumirá como inicio la fecha de 30 días previos a la fecha actual.  
Si se provee startDate pero no se provee un endDate, se asumirá la fecha actual.

Este reporte deberá agrupar datos meteorológicos por ciudad, presentando un orden ascendente por nombre de ciudad.  
Además, cada nodo de agrupación de datos meteorológico deberá tener un campo **lowest** y **highest**, representando la temperatura más baja y la más alta, respectivamente, para la ciudad en las fechas seleccionadas.  
Si no hay datos de temperatura para la ciudad en estas fechas, un campo **message** deberá informar *"No se encontraron datos meteorológicos en el intervalo de fechas solicitado"*.

# Consideraciones adicionales

Para la versión inicial de este proyecto, ustedes han decidido que una base de datos en memoria (tipo H2) es lo indicado  para mantener los datos iniciales y realizar pruebas de integración, por lo cual al menos 5 registros serían de utilidad en el proceso de codificación.

Por solicitud de tu amiga, el servicio debe levantarse en el puerto 8081 y con el seniority que te caracteriza podrás desarrollar esta solución con los estándares de calidad suficientes para ser puesta en producción inmediatamente.

# Reto

+ Implementa los endpoints descritos arriba.
+ Utiliza particularidades de Java 8 o superior (Lambdas, Streams, Functionals, Time API, Collections API improvements).
+ Define un modelo de datos y configura una carga inicial de datos de pruebas en una BD en memoria.
+ Todo lo que no esté explicitamente indicado queda a tu criterio
+ El código debe estar escrito íntegramente en inglés
