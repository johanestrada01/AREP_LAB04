# Servidor Web en Java

## Descripción

Este proyecto implementa un FrameWork web en Java utilizando solo las bibliotecas estándar de Java, sin la necesidad de frameworks como Spring o Spark. El servidor maneja solicitudes HTTP en el puerto indicado por el usuario y puede servir diferentes tipos de contenido, incluyendo HTML, imágenes, archivos JavaScript, hojas de estilo CSS. El servidor provee etiquetas para que el usuario marque los componentes creados. Para esté caso se tiene una instancia del servidor desplegado en una maquina virtual de aws, podemos acceder a la máquina por http://54.91.198.107:35000. Para realizar el despliegue se utiliza un contenedor docker, usando una imagen cargada en dockerhub.

Se incluyen clases de prueba para que el usuario observe el uso del framework.

### Características principales

- Soporte para solicitudes **GET**.
- Capacidad para servir **archivos estáticos** (HTML, CSS, JavaScript, imágenes).
- Gestión sencilla de solicitudes en un único hilo (sin concurrencia).
- Soporte de etiquetas @RestController, @GetMapping y @RequestParam para definir los componentes del servidor.


---

## Instalación

### Prerrequisitos
- Java 21
- Git
- Navegador web
- Maven

### Clonación del repositorio
```sh
 git clone https://github.com/johanestrada01/AREP_LAB03.git
 cd AREP_LAB03
 mvn clean install
 java -cp target/classes Lab03.app
```

### Uso
- El usuario puede extender la clase MicroServer que le va a proporcionar metodos para iniciar un servicio web. El usuario debe crear los metodos REST por medio de etiquetas @RestController para la clase y @GetMapping para los metodos junto con su ruta, los parametros deben marcarse con @RequestParam y puede iniciar el servicio por el puerto deseado, usando el metodo startServer.

### Acceso
- Ingresar a http://54.91.198.107:35000/ junto con las rutas de los metodos definidos.

## Arquitectura
![Arquitectura](https://www.cablenaranja.com/wp-content/uploads/2021/08/Introduccion-Al-HTML-CableNaranja-1.png)

### Servicios REST

- http://54.91.198.107:35000/ -> Nos lleva a una página index que permite  hacer una redirección.

## Pruebas

### Despliegue

![Prueba](https://drive.google.com/file/d/1JuBy2K3OKNBWesNiCiyoS-ElvTKLIR39/view?usp=drive_link)

### Automatizadas
- Ejecutar con:
```sh
 mvn test
```
## Tecnologias
- Java
- Maven
- Html
- JavaScript
- CSS
- Docker

## Autores
- Johan Alejandro Estrada Pastran
