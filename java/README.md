# Aplicacion Java - CRM Instituto Xtart

Aplicacion de consola desarrollada en Java que permite gestionar las entidades
del CRM (alumnos, personal, cursos y matriculas) mediante operaciones CRUD
sobre una base de datos MySQL, usando JDBC.

## Arquitectura

El proyecto sigue una arquitectura en capas:

- `entities` : clases del modelo. Cliente y Usuario heredan de la clase
  abstracta Persona.
- `repository` : acceso a datos con JDBC. Cada repositorio implementa una
  interfaz generica (patron Repository).
- `service` : logica de negocio y validaciones.
- `interfaz` : menu de navegacion por consola.
- `util` : configuracion de la conexion, pool de conexiones y exportador a CSV.
- `exceptions` : excepcion propia para entidades no encontradas.

## Requisitos previos

- Java JDK 17 o superior.
- IntelliJ IDEA.
- MySQL Server arrancado en el puerto 3307.
- Driver JDBC de MySQL (archivo mysql-connector-j-x.x.x.jar).

## Configuracion de la base de datos

La aplicacion se conecta a una base de datos MySQL llamada crm_xtart que debe
estar escuchando en el puerto 3307. Antes de ejecutar la aplicacion hay que
crear la base de datos ejecutando el script mysql_crm_xtart.sql de la carpeta
base-de-datos.

Los datos de conexion se leen del fichero config.properties. Para que la
aplicacion lo encuentre al ejecutar desde IntelliJ, el fichero debe estar en la
raiz del proyecto (la carpeta que se abre en IntelliJ). Tambien se incluye una
copia dentro de la carpeta java. Ejemplo de contenido:

```
db.host=localhost
db.puerto=3307
db.nombre=crm_xtart
db.usuario=root
db.password=
db.pool.tamano=5
```

Hay que poner la contrasena real de MySQL en db.password. Si se usa XAMPP, el
usuario root suele tener la contrasena vacia, por lo que se deja la linea tal
cual (db.password= sin nada despues del igual).

Nota: si la aplicacion no encuentra el fichero, usa unos valores por defecto
(puerto 3307 y contrasena vacia), que coinciden con una instalacion tipica de
XAMPP, por lo que puede conectar igualmente.

## Anadir el driver JDBC en IntelliJ

1. Copiar el archivo mysql-connector-j-x.x.x.jar a la carpeta lib del proyecto.
2. Abrir File, Project Structure, Modules, pestana Dependencies.
3. Pulsar el boton mas, JARs or Directories, y seleccionar el jar de la carpeta lib.
4. Aplicar y aceptar.

Tambien se puede hacer clic derecho sobre el jar en la carpeta lib y elegir la
opcion Add as Library.

## Como ejecutar

1. Comprobar que MySQL esta arrancado en el puerto 3307 y que la base de datos
   crm_xtart existe con sus tablas y datos (ejecutar antes mysql_crm_xtart.sql).
2. Comprobar que el fichero config.properties tiene la contrasena correcta (o
   vacia si se usa XAMPP).
3. Comprobar que el driver JDBC esta anadido como dependencia.
4. Ejecutar la clase Main desde IntelliJ.

Al arrancar aparece un menu de consola desde el que se accede a la gestion de
cada entidad y a los informes.

## Funcionalidades

- CRUD completo de las cinco entidades.
- Matricula de un alumno en uno o varios cursos como una transaccion: si algo
  falla durante el proceso se hace rollback y no se guarda nada a medias.
- Informes (facturacion, matriculas pendientes, matriculas por comercial).
- Exportacion de datos a fichero CSV.
