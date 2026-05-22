class Cliente {
  constructor(id, nombre, email, telefono, direccion) {
    this.id = id;
    this.nombre = nombre;
    this.email = email;
    this.telefono = telefono;
    this.direccion = direccion;
  }
}

class Usuario {
  constructor(id, nombre, email, rol) {
    this.id = id;
    this.nombre = nombre;
    this.email = email;
    this.rol = rol;
  }
}

class Producto {
  constructor(id, nombre, descripcion, precio, categoria) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.precio = precio;
    this.categoria = categoria;
  }
}

class Venta {
  constructor(id, clienteId, usuarioId, fecha, estado, total) {
    this.id = id;
    this.clienteId = clienteId;
    this.usuarioId = usuarioId;
    this.fecha = fecha;
    this.estado = estado;
    this.total = total;
  }
}

class DetalleVenta {
  constructor(id, ventaId, productoId, cantidad, precioUnitario) {
    this.id = id;
    this.ventaId = ventaId;
    this.productoId = productoId;
    this.cantidad = cantidad;
    this.precioUnitario = precioUnitario;
  }
}

class Almacen {
  constructor() {
    this.datos = {
      clientes: [],
      usuarios: [],
      productos: [],
      ventas: [],
      detalles: []
    };
    this.cargar();
  }

  cargar() {
    const guardado = sessionStorage.getItem('crm_xtart');
    if (guardado) {
      this.datos = JSON.parse(guardado);
    } else {
      this.datosIniciales();
      this.guardar();
    }
  }

  guardar() {
    sessionStorage.setItem('crm_xtart', JSON.stringify(this.datos));
  }

  lista(entidad) {
    return this.datos[entidad];
  }

  siguienteId(entidad) {
    const lista = this.datos[entidad];
    let max = 0;
    lista.forEach(item => {
      if (item.id > max) {
        max = item.id;
      }
    });
    return max + 1;
  }

  anadir(entidad, objeto) {
    this.datos[entidad].push(objeto);
    this.guardar();
  }

  actualizar(entidad, objeto) {
    const lista = this.datos[entidad];
    for (let i = 0; i < lista.length; i++) {
      if (lista[i].id === objeto.id) {
        lista[i] = objeto;
        break;
      }
    }
    this.guardar();
  }

  borrar(entidad, id) {
    this.datos[entidad] = this.datos[entidad].filter(item => item.id !== id);
    this.guardar();
  }

  buscarPorId(entidad, id) {
    return this.datos[entidad].find(item => item.id === id);
  }

  datosIniciales() {
    this.datos.clientes = [
      new Cliente(1, 'Omar Yamani', 'omar.yamani@estudiante.xtart.com', '666111222', 'Calle Mayor 1, Madrid'),
      new Cliente(2, 'Daniel Vargas', 'daniel.vargas@estudiante.xtart.com', '666333444', 'Av. Libertad 23, Sevilla'),
      new Cliente(3, 'Alvaro Mendez', 'alvaro.mendez@estudiante.xtart.com', '655987321', 'Plaza Espana 5, Valencia'),
      new Cliente(4, 'Berta Ramirez', 'berta.ramirez@estudiante.xtart.com', '677456123', 'Calle Sol 14, Madrid'),
      new Cliente(5, 'Lihuak Romero', 'lihuak.romero@estudiante.xtart.com', '688234567', 'Av. Andalucia 8, Malaga'),
      new Cliente(6, 'Diego Fuentes', 'diego.fuentes@estudiante.xtart.com', '699112233', 'Calle Luna 22, Bilbao'),
      new Cliente(7, 'Renzo Aguilar', 'renzo.aguilar@estudiante.xtart.com', '644556677', 'Paseo del Mar 3, Alicante'),
      new Cliente(8, 'Noemi Cabrera', 'noemi.cabrera@estudiante.xtart.com', '633778899', 'Calle Real 41, Granada'),
      new Cliente(9, 'Laura Iglesias', 'laura.iglesias@estudiante.xtart.com', '622334455', 'Av. Constitucion 12, Zaragoza'),
      new Cliente(10, 'Alejandro Pardo', 'alejandro.pardo@estudiante.xtart.com', '611223344', 'Calle Nueva 7, Murcia'),
      new Cliente(11, 'Carlos Benitez', 'carlos.benitez@estudiante.xtart.com', '600998877', 'Plaza Mayor 9, Salamanca'),
      new Cliente(12, 'Dani Salas', 'dani.salas@estudiante.xtart.com', '655443322', 'Calle Iglesia 2, Cordoba'),
      new Cliente(13, 'Berme Lozano', 'berme.lozano@estudiante.xtart.com', '677889900', 'Av. Europa 30, Valladolid'),
      new Cliente(14, 'Javi Campos', 'javi.campos@estudiante.xtart.com', '688776655', 'Calle Olivo 18, Jaen'),
      new Cliente(15, 'Juanda Reyes', 'juanda.reyes@estudiante.xtart.com', '699665544', 'Av. del Puerto 5, Cadiz'),
      new Cliente(16, 'Helen Carmona', 'helen.carmona@estudiante.xtart.com', '644778822', 'Calle Rosales 11, Huelva'),
      new Cliente(17, 'Sergio Marquez', 'sergio.marquez@estudiante.xtart.com', '633112299', 'Plaza Nueva 8, Almeria'),
      new Cliente(18, 'Mariana Acosta', 'mariana.acosta@estudiante.xtart.com', '622889911', 'Av. Reyes 14, Toledo'),
      new Cliente(19, 'Culindeamigo Rios', 'culindeamigo.rios@estudiante.xtart.com', '611445566', 'Calle Parra 3, Caceres'),
      new Cliente(20, 'Dyron Espinoza', 'dyron.espinoza@estudiante.xtart.com', '600334477', 'Av. Mar 21, Tarragona')
    ];
    this.datos.usuarios = [
      new Usuario(1, 'Nuria Soto', 'nuria.soto@xtart.com', 'COMERCIAL'),
      new Usuario(2, 'Daniel Jaen', 'daniel.jaen@xtart.com', 'ORIENTADOR'),
      new Usuario(3, 'Alberto Nieto', 'alberto.nieto@xtart.com', 'ADMIN'),
      new Usuario(4, 'Sonia Prieto', 'sonia.prieto@xtart.com', 'SECRETARIA'),
      new Usuario(5, 'Aitor Gallego', 'aitor.gallego@xtart.com', 'COMERCIAL'),
      new Usuario(6, 'Lorena Sanz', 'lorena.sanz@xtart.com', 'ORIENTADOR')
    ];
    this.datos.productos = [
      new Producto(1, 'DAM', 'Desarrollo de Aplicaciones Multiplataforma', 1200, 'GRADO_SUPERIOR'),
      new Producto(2, 'SMR', 'Sistemas Microinformaticos y Redes', 900, 'GRADO_MEDIO'),
      new Producto(3, 'DAW', 'Desarrollo de Aplicaciones Web', 1200, 'GRADO_SUPERIOR'),
      new Producto(4, 'ASIR', 'Administracion de Sistemas Informaticos en Red', 1150, 'GRADO_SUPERIOR'),
      new Producto(5, 'Ingles B2', 'Preparacion oficial nivel B2', 350, 'IDIOMAS'),
      new Producto(6, 'Ciberseguridad', 'Curso de especializacion en ciberseguridad', 800, 'ESPECIALIZACION'),
      new Producto(7, 'Ofimatica', 'Curso corto de ofimatica avanzada', 200, 'CURSO_CORTO'),
      new Producto(8, 'Big Data e IA', 'Especializacion en inteligencia artificial y Big Data', 850, 'ESPECIALIZACION'),
      new Producto(9, 'Aleman A2', 'Curso de aleman nivel A2', 320, 'IDIOMAS'),
      new Producto(10, 'GAD', 'Gestion Administrativa', 850, 'GRADO_MEDIO'),
      new Producto(11, 'Marketing Digital', 'Curso corto de marketing digital', 250, 'CURSO_CORTO'),
      new Producto(12, 'Cloud AWS', 'Especializacion en computacion en la nube', 780, 'ESPECIALIZACION')
    ];
    this.datos.ventas = [
      new Venta(1, 1, 1, '2026-01-15', 'PAGADA', 1200),
      new Venta(2, 2, 5, '2026-02-03', 'CONFIRMADA', 900),
      new Venta(3, 3, 1, '2026-02-20', 'PAGADA', 1550),
      new Venta(4, 4, 5, '2026-03-10', 'PENDIENTE', 1200),
      new Venta(5, 5, 2, '2026-03-22', 'PAGADA', 350),
      new Venta(6, 6, 1, '2026-04-05', 'CONFIRMADA', 800),
      new Venta(7, 7, 5, '2026-04-18', 'CANCELADA', 200),
      new Venta(8, 8, 2, '2026-05-02', 'PENDIENTE', 850),
      new Venta(9, 9, 3, '2026-05-08', 'PAGADA', 1150),
      new Venta(10, 10, 5, '2026-05-12', 'CONFIRMADA', 320),
      new Venta(11, 11, 1, '2026-05-15', 'PAGADA', 780),
      new Venta(12, 12, 5, '2026-05-18', 'PENDIENTE', 850),
      new Venta(13, 13, 2, '2026-05-20', 'CONFIRMADA', 250),
      new Venta(14, 14, 2, '2026-05-22', 'PAGADA', 1200)
    ];
    this.datos.detalles = [
      new DetalleVenta(1, 1, 1, 1, 1200),
      new DetalleVenta(2, 2, 2, 1, 900),
      new DetalleVenta(3, 3, 3, 1, 1200),
      new DetalleVenta(4, 3, 5, 1, 350),
      new DetalleVenta(5, 4, 1, 1, 1200),
      new DetalleVenta(6, 5, 5, 1, 350),
      new DetalleVenta(7, 6, 6, 1, 800),
      new DetalleVenta(8, 7, 7, 1, 200),
      new DetalleVenta(9, 8, 8, 1, 850),
      new DetalleVenta(10, 9, 4, 1, 1150),
      new DetalleVenta(11, 10, 9, 1, 320),
      new DetalleVenta(12, 11, 12, 1, 780),
      new DetalleVenta(13, 12, 8, 1, 850),
      new DetalleVenta(14, 13, 11, 1, 250),
      new DetalleVenta(15, 14, 1, 1, 1200)
    ];
  }
}

const CONFIG = {
  clientes: {
    titulo: 'Alumnos',
    columnas: ['id', 'nombre', 'email', 'telefono', 'direccion'],
    campos: [
      { clave: 'nombre', etiqueta: 'Nombre', tipo: 'text' },
      { clave: 'email', etiqueta: 'Email', tipo: 'email' },
      { clave: 'telefono', etiqueta: 'Telefono', tipo: 'text' },
      { clave: 'direccion', etiqueta: 'Direccion', tipo: 'text' }
    ],
    crear: (id, v) => new Cliente(id, v.nombre, v.email, v.telefono, v.direccion)
  },
  usuarios: {
    titulo: 'Personal',
    columnas: ['id', 'nombre', 'email', 'rol'],
    campos: [
      { clave: 'nombre', etiqueta: 'Nombre', tipo: 'text' },
      { clave: 'email', etiqueta: 'Email', tipo: 'email' },
      { clave: 'rol', etiqueta: 'Rol', tipo: 'select', opciones: ['ADMIN', 'ORIENTADOR', 'SECRETARIA', 'COMERCIAL'] }
    ],
    crear: (id, v) => new Usuario(id, v.nombre, v.email, v.rol)
  },
  productos: {
    titulo: 'Cursos',
    columnas: ['id', 'nombre', 'categoria', 'precio'],
    campos: [
      { clave: 'nombre', etiqueta: 'Nombre', tipo: 'text' },
      { clave: 'descripcion', etiqueta: 'Descripcion', tipo: 'text' },
      { clave: 'precio', etiqueta: 'Precio', tipo: 'number' },
      { clave: 'categoria', etiqueta: 'Categoria', tipo: 'select', opciones: ['GRADO_MEDIO', 'GRADO_SUPERIOR', 'ESPECIALIZACION', 'CURSO_CORTO', 'IDIOMAS'] }
    ],
    crear: (id, v) => new Producto(id, v.nombre, v.descripcion, Number(v.precio), v.categoria)
  },
  ventas: {
    titulo: 'Matriculas',
    columnas: ['id', 'clienteId', 'usuarioId', 'fecha', 'estado', 'total'],
    campos: [
      { clave: 'clienteId', etiqueta: 'Id alumno', tipo: 'number' },
      { clave: 'usuarioId', etiqueta: 'Id personal', tipo: 'number' },
      { clave: 'fecha', etiqueta: 'Fecha', tipo: 'date' },
      { clave: 'estado', etiqueta: 'Estado', tipo: 'select', opciones: ['PENDIENTE', 'CONFIRMADA', 'PAGADA', 'CANCELADA'] },
      { clave: 'total', etiqueta: 'Total', tipo: 'number' }
    ],
    crear: (id, v) => new Venta(id, Number(v.clienteId), Number(v.usuarioId), v.fecha, v.estado, Number(v.total))
  },
  detalles: {
    titulo: 'Detalle matriculas',
    columnas: ['id', 'ventaId', 'productoId', 'cantidad', 'precioUnitario'],
    campos: [
      { clave: 'ventaId', etiqueta: 'Id matricula', tipo: 'number' },
      { clave: 'productoId', etiqueta: 'Id curso', tipo: 'number' },
      { clave: 'cantidad', etiqueta: 'Cantidad', tipo: 'number' },
      { clave: 'precioUnitario', etiqueta: 'Precio unitario', tipo: 'number' }
    ],
    crear: (id, v) => new DetalleVenta(id, Number(v.ventaId), Number(v.productoId), Number(v.cantidad), Number(v.precioUnitario))
  }
};

const almacen = new Almacen();
let entidadActiva = 'clientes';
let editandoId = null;

const tablaZona = document.getElementById('tabla-zona');
const buscador = document.getElementById('buscador');
const modal = document.getElementById('modal');
const modalCuerpo = document.getElementById('modal-cuerpo');
const modalTitulo = document.getElementById('modal-titulo');

function pintarStats() {
  const stats = [
    { etiqueta: 'Alumnos', n: almacen.lista('clientes').length },
    { etiqueta: 'Personal', n: almacen.lista('usuarios').length },
    { etiqueta: 'Cursos', n: almacen.lista('productos').length },
    { etiqueta: 'Matriculas', n: almacen.lista('ventas').length }
  ];
  let html = '';
  stats.forEach(s => {
    html += '<div class="kpi">';
    html += '<span class="kpi__etiqueta">' + s.etiqueta + '</span>';
    html += '<span class="kpi__numero">' + s.n + '</span>';
    html += '<span class="kpi__pie"></span>';
    html += '</div>';
  });
  document.getElementById('resumen').innerHTML = html;
}

function pintarTabla() {
  pintarStats();
  const config = CONFIG[entidadActiva];
  const filtro = buscador.value.toLowerCase().trim();

  const lista = almacen.lista(entidadActiva).filter(item => {
    if (!filtro) {
      return true;
    }
    const texto = Object.values(item).join(' ').toLowerCase();
    return texto.includes(filtro);
  });

  if (lista.length === 0) {
    tablaZona.innerHTML = '<p class="tabla__vacia">No hay registros</p>';
    return;
  }

  let html = '<table class="tabla"><thead class="tabla__cabecera"><tr>';
  config.columnas.forEach(col => {
    html += '<th class="tabla__th">' + col + '</th>';
  });
  html += '<th class="tabla__th">Acciones</th></tr></thead><tbody>';

  lista.forEach(item => {
    html += '<tr class="tabla__fila">';
    config.columnas.forEach(col => {
      let valor = item[col];
      if (col === 'categoria' || col === 'estado' || col === 'rol') {
        valor = '<span class="pildora pildora--' + valor + '">' + valor + '</span>';
      }
      html += '<td class="tabla__td">' + valor + '</td>';
    });
    html += '<td class="tabla__td"><div class="tabla__acciones">';
    html += '<button class="tabla__btn tabla__btn--editar" data-accion="editar" data-id="' + item.id + '">Editar</button>';
    html += '<button class="tabla__btn tabla__btn--borrar" data-accion="borrar" data-id="' + item.id + '">Borrar</button>';
    html += '</div></td></tr>';
  });

  html += '</tbody></table>';
  tablaZona.innerHTML = html;
}

function abrirModal(id) {
  const config = CONFIG[entidadActiva];
  editandoId = id;

  let objeto = null;
  if (id !== null) {
    objeto = almacen.buscarPorId(entidadActiva, id);
    modalTitulo.textContent = 'Editar ' + config.titulo;
  } else {
    modalTitulo.textContent = 'Nuevo en ' + config.titulo;
  }

  let html = '';
  config.campos.forEach(campo => {
    const valor = objeto ? objeto[campo.clave] : '';
    html += '<div class="modal__campo">';
    html += '<label class="modal__label">' + campo.etiqueta + '</label>';

    if (campo.tipo === 'select') {
      html += '<select class="modal__input" id="campo-' + campo.clave + '">';
      campo.opciones.forEach(op => {
        const sel = (valor === op) ? ' selected' : '';
        html += '<option value="' + op + '"' + sel + '>' + op + '</option>';
      });
      html += '</select>';
    } else {
      html += '<input class="modal__input" id="campo-' + campo.clave + '" type="' + campo.tipo + '" value="' + valor + '">';
    }

    html += '<span class="modal__error" id="error-' + campo.clave + '"></span>';
    html += '</div>';
  });

  modalCuerpo.innerHTML = html;
  modal.classList.add('modal--abierto');
}

function cerrarModal() {
  modal.classList.remove('modal--abierto');
  editandoId = null;
}

function guardar() {
  const config = CONFIG[entidadActiva];
  const valores = {};
  let hayError = false;

  config.campos.forEach(campo => {
    const input = document.getElementById('campo-' + campo.clave);
    const error = document.getElementById('error-' + campo.clave);
    const valor = input.value.trim();
    error.textContent = '';

    if (valor === '') {
      error.textContent = 'Este campo es obligatorio';
      hayError = true;
    }
    valores[campo.clave] = valor;
  });

  if (hayError) {
    return;
  }

  if (editandoId !== null) {
    const objeto = config.crear(editandoId, valores);
    almacen.actualizar(entidadActiva, objeto);
    mostrarAviso('Registro actualizado');
  } else {
    const nuevoId = almacen.siguienteId(entidadActiva);
    const objeto = config.crear(nuevoId, valores);
    almacen.anadir(entidadActiva, objeto);
    mostrarAviso('Registro creado');
  }

  cerrarModal();
  pintarTabla();
}

function borrar(id) {
  const ok = window.confirm('Seguro que quieres borrar el registro ' + id + '?');
  if (ok) {
    almacen.borrar(entidadActiva, id);
    mostrarAviso('Registro borrado');
    pintarTabla();
  }
}

function lanzarAviso(texto, alTerminar) {
  const aviso = document.getElementById('aviso');
  aviso.textContent = texto;
  aviso.classList.add('aviso--visible');

  setTimeout(() => {
    aviso.classList.remove('aviso--visible');
    if (typeof alTerminar === 'function') {
      alTerminar();
    }
  }, 2000);
}

function mostrarAviso(texto) {
  lanzarAviso(texto, null);
}

function actualizarViewport() {
  const ancho = window.innerWidth;
  let tipo = 'Escritorio';
  if (ancho < 640) {
    tipo = 'Movil';
  } else if (ancho < 992) {
    tipo = 'Tablet';
  }
  document.getElementById('viewport-info').textContent = tipo;
}

document.addEventListener('DOMContentLoaded', () => {

  const botonesTab = document.querySelectorAll('.nav__item');
  botonesTab.forEach(btn => {
    btn.addEventListener('click', () => {
      botonesTab.forEach(b => b.classList.remove('nav__item--active'));
      btn.classList.add('nav__item--active');
      entidadActiva = btn.dataset.entidad;
      const titulo = CONFIG[entidadActiva].titulo;
      document.getElementById('migas-actual').textContent = titulo;
      document.getElementById('panel-titulo').textContent = 'Listado de ' + titulo;
      buscador.value = '';
      pintarTabla();
    });
  });

  document.getElementById('btn-nuevo').addEventListener('click', () => abrirModal(null));

  document.getElementById('modal-cerrar').addEventListener('click', cerrarModal);
  document.getElementById('btn-cancelar').addEventListener('click', cerrarModal);
  document.getElementById('btn-guardar').addEventListener('click', guardar);

  buscador.addEventListener('input', pintarTabla);

  tablaZona.addEventListener('click', (e) => {
    const boton = e.target.closest('button');
    if (!boton) {
      return;
    }
    const id = Number(boton.dataset.id);
    if (boton.dataset.accion === 'editar') {
      abrirModal(id);
    } else if (boton.dataset.accion === 'borrar') {
      borrar(id);
    }
  });

  window.addEventListener('click', (e) => {
    if (e.target === modal) {
      cerrarModal();
    }
  });

  actualizarViewport();
  window.addEventListener('resize', actualizarViewport);

  pintarTabla();
});
