# Base de datos - CRM Instituto Xtart

Esta carpeta contiene los scripts de la base de datos del proyecto, tanto en
MySQL (la que usa la aplicacion Java) como en Oracle (con PL/SQL).

## Archivos

- `mysql_crm_xtart.sql` : script para MySQL. Crea la base de datos crm_xtart,
  las cinco tablas e inserta los datos. Es la base a la que se conecta la
  aplicacion Java.

- `oracle_1_ddl_dml.sql` : creacion de las cinco tablas (DDL) e insercion inicial.

- `oracle_2_cursores.sql` : 25 consultas con cursores (5 por cada tabla).

- `oracle_3_procedimientos_funciones.sql` : 10 procedimientos y 10 funciones
  (2 y 2 por cada tabla) y dos vistas.

- `oracle_4_dml_plsql.sql` : manipulacion de datos (DML) a traves de bloques
  PL/SQL. Por cada tabla crea, modifica y elimina 5 registros usando bucles,
  variables, control y manejo de excepciones con ROLLBACK.

## Modelo

Cinco tablas: clientes, usuarios, productos, ventas y detalle_venta.
Relaciones 1:N y N:M (resuelta con detalle_venta). Tercera forma normal.

## Como ejecutar

Oracle SQL Developer: ejecutar los scripts en orden (1, 2, 3, 4) con Ejecutar
Script (F5). Activar antes SET SERVEROUTPUT ON para ver la salida.

MySQL Workbench: abrir mysql_crm_xtart.sql y ejecutarlo con Ejecutar Script.
