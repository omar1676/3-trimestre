# CRM Instituto Xtart — Aplicación Web (Lenguaje de Marcas)

Parte visual del CRM hecha con HTML, CSS y JavaScript puro (sin frameworks).
Gestiona las mismas entidades que la base de datos del proyecto: alumnos
(clientes), personal (usuarios), cursos (productos), matrículas (ventas) y
el detalle de cada matrícula.

## Cómo ejecutarla

No necesita servidor ni instalación. Basta con abrir el archivo `index.html`
en cualquier navegador (doble clic, o clic derecho → Abrir con...).

## Archivos

- `index.html` — estructura de la página (HTML semántico)
- `style.css` — estilos con metodología BEM y diseño responsive
- `app.js` — lógica: clases, CRUD y manipulación del DOM

## Qué hace

- CRUD completo (crear, leer, editar, borrar) para las 5 entidades.
- Los datos se guardan en SessionStorage: aguantan al refrescar la página,
  pero se reinician al cerrar el navegador (no hay backend, es lo pedido).
- Buscador en tiempo real sobre cada tabla.
- Aviso visual al crear, editar o borrar.
- Indicador de viewport (móvil / tablet / escritorio) que se actualiza al
  redimensionar la ventana.

## Notas técnicas (para la defensa)

- **Clases**: hay una clase por entidad (Cliente, Usuario, Producto, Venta,
  DetalleVenta) más una clase `Almacen` que gestiona las listas y el
  SessionStorage.
- **Funciones flecha vs tradicionales**: las funciones principales
  (pintarTabla, guardar, borrar) son tradicionales por claridad; las pequeñas
  de callback (forEach, addEventListener, filter) son flecha por brevedad.
- **Callback**: la función `lanzarAviso` recibe una función que ejecuta al
  ocultarse el aviso.
- **Objeto Window / viewport**: se usa `window.innerWidth`, el evento
  `resize` y `window.confirm` para borrar.
- **Eventos del DOM**: navegación por pestañas, búsqueda, y delegación de
  eventos en la tabla (un solo listener para editar y borrar).
- **BEM**: nomenclatura bloque__elemento--modificador en todo el CSS.
