# 75.52 - Taller de Programación 2

Este repositorio contiene el proyecto realizado en la materia (75.52) Taller de
Programación II en la FIUBA.

## Introducción

La aplicación es una aplicación de escritorio orientada al desarrollo de
modelos de software (de entidad-relación, relacionales, de estados) que permite
visualizar y editar los diagramas correspondientes a cada modelo, validar los
modelos de acuerdo a reglas de lint y transformar los modelos automáticamente
entre si.

La aplicación fue desarrollada por tres grupos diferentes en etapas:

1. **1er C. 2012**: Desarrollo de la ui básica, la administración de proyectos
   (cargar, editar y guardar) y el modelado de entidad-interrelación.

2. **2do C. 2012**: Desarrollo del modelado relacional y la transformación del
   diagrama ER a relacional.

3. **1er C. 2013**: Desarrollo del modelado de estados para representación de
   flujos de ui.

## Manejo del proyecto

Toda la información relacionada al proyecto en si, incluyendo requisitos,
preguntas, documentación de referencia, minutas de reuniones, planificaciones,
informes de avances y otros está disponible en la
[wiki](https://github.com/andres-arana/degree-7552-taller-2/wiki).

## Estructura del repositorio

El repositorio está estructurado siguiendo las convenciones de maven y gradle:

* **docs**: Documentación. [TODO: Definir cómo y qué vamos a documentar]

    * **diagramas**: Diagramas uml de la solución desarrollada.

* **src**: Código fuente y todo lo necesario para construir la aplicación, como
  recursos gráficos, icónos y librerías locales.

    * **libs**: Contiene librerías locales no disponibles en los repositorios
      de maven o ivy, necesarias para compilar y ejecutar la aplicación

    * **main**: Contiene el código fuente y recursos del programa en si.

    * **test**: Contiene el código fuente y recursos de la suite de tests
      automatizados. Cabe destacar el subdirectorio `resources/samples`, que
contiene varios archivos de proyectos mda de ejemplo listos para importar en la
aplicación

## Ambiente de desarrollo

### Prerequisitos

La aplicación está desarrollada con java 7, utilizando
[gradle](http://www.gradle.org/) como build system, por lo que será necesario
instalar ambos para poder compilar la aplicación:

1. Java JDK 7: Dependiendo del sistema operativo en el que se vaya a
   desarrollar. Se ha desarrollado tanto en windows con Oracle JDK 7 como en
Ubuntu Linux con OpenJdk 7.

2. Gradle: En [la documentación oficial de
   gradle](http://www.gradle.org/docs/current/userguide/userguide_single.html#installation)
hay una sección sobre su instalación.

### Gradle

Gradle es un build system basado en [Ant](http://ant.apache.org/),
[Maven](http://maven.apache.org/) e [Ivy](http://ant.apache.org/ivy/) que
automatiza el proceso de build, testing y publicación y deploy de paquetes de
software. Es una herramienta de linea de comandos que se encarga de resolver
las dependencias del proyecto, compilarlo, ejecutar los tests y empaquetar el
software.

Desde la linea de comandos, ubicado en el directorio base del proyecto, se
ejecuta `gradle [TASK]` para realizar cada una de estas actividades, donde TASK
es una tarea particular que define qué es lo que se quiere hacer, sea compilar,
ejecutar tests o empaquetar y lanzar la aplicación.

Algunos ejemplos de comandos útiles:

* `gradle test`: Descarga y resuelve las dependencias de la aplicación,
  compila el código fuente, compila los tests y los ejecuta.

* `gradle run`: Idem a `gradle test`, pero además si los tests se ejecutan
  correctamente ejecuta la aplicación en si misma.

* `gradle distZip`: Idem a `gradle test`, pero además si los tests se ejecutan
  correctamente construye un zip de instalación para poder distribuir la
aplicación.

* `gradle clean`: Elimina todos los archivos generados durante el build.

* `gradle tasks`: Lista toda las tareas disponibles.

* `gradle eclipse`: Genera todos los archivos necesarios para importar el
  proyecto en eclipse.

Se recomienda leer la documentación de
[gradle](http://www.gradle.org/docs/current/userguide/userguide_single.html)
para más información.

### Target platforms

La aplicación utiliza [SWT](http://www.eclipse.org/swt/), una librería java
para construir interfaces nativas que tiene componentes dependientes del
sistema operativo en el que se ejecuta la aplicación. El repositorio contiene
todos los archivos necesarios para construir la aplicación directamente con
gradle, detectando la plataforma en la que se ejecuta build e incluyendo las
librerías específicas de dicha plataforma según corresponda.

Se incluye una task `gradle listBuildArchitectures` que lista las plataformas
soportadas en el build.

Además, puede darse instrucciones al build de compilar en una arquitectura
específica a través de la property de gradle `targetArchitecture`. Ejecutar la
tarea `gradle listBuildArchitectures` para más información al erspecto.

