# WatchThis

## Autores
- [Fleischer, Lucas](https://github.com/tomalvarezz)
- [Banfi, Malena](https://github.com/malenabanfi)
- [Perez Rivera, Mateo](https://github.com/mateoperezrivera)
- [Szejer, Ian](https://github.com/IanSzejer)

## Introducción
Este proyecto consiste en el desarrollo de una aplicación web de varios módulos que agrupa tanto el frontend (cliente SPA) como el backend (API REST).

### Backend

La estructura general del proyecto sigue el patrón de MVC, donde:

- interfaces: declara los "contratos" para las implementaciones de `/services` y `/persistence`.
- models: declara los modelos/entidades utilizados
- persistence: es el "puente" entre la base de datos y la aplicación. 
- services: es el "puente" entre los controllers y la capa de persistencia.
- webapp: implementa los endpoints de la API REST, accediendo a los servicios provistos por el modulo con el mismo nombre.

#### Build

Para construir el proyecto correr:
```
mvn clean install 
```

### Frontend

Para levantar el frontend, dirigirse a la carpeta '/frontend' y correr:

```
npm start
```
Nota: Revisar la baseUrl de la API en `/frontend/src/paths/index.js`
Nota: Revisar la configuracion de la base de datos `/wabapp/src/main/java/ar/edu/itba/paw/webapp/config/WebConfig.java`

Para ejecutar los tests correr:
```
npm test
```
Para ejecutar el build de producción correr:
```
npm run build
```

### Generación del war

Para crear el app.war que debe ser deployado en el servidor de la catedra se debe ejecutar: 
```
mvn clean package
```

## Correciones
- El JS tiene cache no condicional, pero el CSS no. Esto era pedido por enunciado y ya había sido corregido en la entrega anterior.
- Los DTOs llevan hipervínculos a recursos que no existen en la aplicación. Por ejemplo, user presenta el atributo isLikeReviewsUrl: "http://pawserver.it.itba.edu.ar/paw-2022b-3/api/users/61/reviewsLiked". Esto es un error conceptual grave
- Exportan un manifest.json pese a no ser una PWA.
