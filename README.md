# Proyecto CRM Instituto Xtart

Proyecto integrado de primer curso de Desarrollo de Aplicaciones Multiplataforma.
El proyecto consiste en un sistema CRM para la gestion de alumnos, personal,
cursos y matriculas de un instituto, e integra los modulos de Base de Datos,
Programacion, Lenguaje de Marcas y Entornos de Desarrollo.

## Autores

- Omar El Yamani
- Alvaro Brasero

## Contenido del repositorio

El repositorio esta organizado en las siguientes carpetas:

- `java` : aplicacion de consola en Java (modulo de Programacion).
- `lenguaje de marcar` : aplicacion web (modulo de Lenguaje de Marcas).
- `base-de-datos` : scripts de la base de datos en Oracle y MySQL (modulo de Base de Datos).

Cada carpeta tiene su propio README con las instrucciones especificas de esa parte.

## Modelo de datos

El nucleo del proyecto son cinco tablas relacionadas:

- clientes (alumnos)
- usuarios (personal del instituto)
- productos (cursos)
- ventas (matriculas)
- detalle_venta (lineas de cada matricula)

Relaciones: un cliente puede tener muchas ventas y un usuario puede gestionar
muchas ventas (1:N). Una venta puede incluir varios productos y un producto
puede estar en varias ventas, relacion N:M resuelta con la tabla intermedia
detalle_venta.

## Requisitos previos

- Java JDK 17 o superior.
- IntelliJ IDEA (o cualquier IDE equivalente).
- MySQL Server, configurado en el puerto 3307.
- Driver JDBC de MySQL (mysql-connector-j).
- Oracle Database o acceso a Oracle Live SQL para la parte de PL/SQL.
- Un navegador web para la aplicacion de Lenguaje de Marcas.

## Orden recomendado para poner en marcha el proyecto

1. Montar la base de datos siguiendo el README de la carpeta `base-de-datos`.
2. Configurar y ejecutar la aplicacion Java siguiendo el README de la carpeta `java`.
3. Abrir la aplicacion web siguiendo el README de la carpeta `lenguaje de marcar`.

## Control de versiones

El proyecto se ha desarrollado con Git y GitHub usando ramas separadas para
cada integrante (omar y alvaro) y una rama principal (main) donde se integra
el trabajo mediante merges. Se han utilizado tags para marcar versiones y se
han resuelto conflictos de integracion durante el desarrollo.
