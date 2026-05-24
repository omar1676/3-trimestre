package com.omar.interfaz;

import com.omar.entities.Cliente;
import com.omar.entities.DetalleVenta;
import com.omar.entities.Producto;
import com.omar.entities.Usuario;
import com.omar.entities.Venta;
import com.omar.exceptions.EntidadNoEncontradaException;
import com.omar.service.ClienteService;
import com.omar.service.DetalleVentaService;
import com.omar.service.MatriculaService;
import com.omar.service.ProductoService;
import com.omar.service.UsuarioService;
import com.omar.service.VentaService;
import com.omar.util.ConnectionPool;
import com.omar.util.ExportadorCSV;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final Scanner entrada = new Scanner(System.in);

    private ClienteService clienteService;
    private UsuarioService usuarioService;
    private ProductoService productoService;
    private VentaService ventaService;
    private DetalleVentaService detalleService;
    private MatriculaService matriculaService;

    public void start() {
        try {
            clienteService = new ClienteService();
            usuarioService = new UsuarioService();
            productoService = new ProductoService();
            ventaService = new VentaService();
            detalleService = new DetalleVentaService();
            matriculaService = new MatriculaService();
        } catch (SQLException e) {
            System.err.println("Error conectando con la base de datos: " + e.getMessage());
            System.err.println("Revisa config.properties (puerto 3307, usuario, password) y que MySQL este arrancado.");
            return;
        }

        int opcion;
        do {
            showMainMenu();
            opcion = readInt("Opcion: ");
            switch (opcion) {
                case 1:
                    clientsMenu();
                    break;
                case 2:
                    usersMenu();
                    break;
                case 3:
                    productsMenu();
                    break;
                case 4:
                    salesMenu();
                    break;
                case 5:
                    saleDetailsMenu();
                    break;
                case 6:
                    reportsMenu();
                    break;
                case 7:
                    exportMenu();
                    break;
                case 0:
                    System.out.println("Cerrando...");
                    break;
                default:
                    System.out.println("Esa opcion no existe, prueba otra");
            }
        } while (opcion != 0);

        try {
            ConnectionPool.getInstance().closeAll();
        } catch (SQLException ignored) {
        }
    }

    private void showMainMenu() {
        System.out.println();
        System.out.println("+======================================+");
        System.out.println("|        CRM - INSTITUTO XTART         |");
        System.out.println("+======================================+");
        System.out.println("| 1. Clientes (alumnos)                |");
        System.out.println("| 2. Usuarios (personal)               |");
        System.out.println("| 3. Productos (cursos)                |");
        System.out.println("| 4. Ventas (matriculas)               |");
        System.out.println("| 5. Detalles de venta                 |");
        System.out.println("| 6. Informes                          |");
        System.out.println("| 7. Exportar a CSV                    |");
        System.out.println("| 0. Salir                             |");
        System.out.println("+======================================+");
    }

    private void clientsMenu() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- CLIENTES ---");
            System.out.println("1. Anadir");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por id");
            System.out.println("4. Modificar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            opcion = readInt("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        addClient();
                        break;
                    case 2:
                        listClients();
                        break;
                    case 3:
                        findClient();
                        break;
                    case 4:
                        updateClient();
                        break;
                    case 5:
                        deleteClient();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Esa opcion no existe, prueba otra");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void addClient() throws SQLException {
        System.out.print("Nombre: ");
        String nombre = entrada.nextLine();
        System.out.print("Email: ");
        String email = entrada.nextLine();
        System.out.print("Telefono: ");
        String telefono = entrada.nextLine();
        System.out.print("Direccion: ");
        String direccion = entrada.nextLine();
        Cliente c = new Cliente(0, nombre, email, telefono, direccion);
        if (clienteService.add(c)) {
            System.out.println("Cliente guardado correctamente (id " + c.getId() + ")");
        } else {
            System.out.println("Esos datos no valen, revisa el nombre y el email");
        }
    }

    private void listClients() throws SQLException {
        List<Cliente> lista = clienteService.list();
        if (lista.isEmpty()) {
            System.out.println("Todavia no hay clientes");
            return;
        }
        System.out.printf("%-4s %-20s %-28s %-12s %-25s%n", "ID", "NOMBRE", "EMAIL", "TELEFONO", "DIRECCION");
        System.out.println("-------------------------------------------------------------------------------------------");
        for (Cliente c : lista) {
            System.out.printf("%-4d %-20s %-28s %-12s %-25s%n",
                    c.getId(), c.getName(), c.getEmail(), c.getPhone(), c.getAddress());
        }
    }

    private void findClient() throws SQLException {
        int id = readInt("Id: ");
        Cliente c = clienteService.find(id);
        if (c == null) {
            System.out.println("No hay ningun cliente con ese id");
        } else {
            System.out.println(c);
        }
    }

    private void updateClient() throws SQLException {
        int id = readInt("Id: ");
        Cliente c = clienteService.find(id);
        if (c == null) {
            System.out.println("No hay ningun cliente con ese id");
            return;
        }
        System.out.print("Nuevo nombre: ");
        c.setName(entrada.nextLine());
        System.out.print("Nuevo email: ");
        c.setEmail(entrada.nextLine());
        System.out.print("Nuevo telefono: ");
        c.setPhone(entrada.nextLine());
        System.out.print("Nueva direccion: ");
        c.setAddress(entrada.nextLine());
        if (clienteService.modify(c)) {
            System.out.println("Cambios guardados");
        } else {
            System.out.println("No se ha podido guardar");
        }
    }

    private void deleteClient() throws SQLException {
        int id = readInt("Id: ");
        if (clienteService.remove(id)) {
            System.out.println("Borrado correctamente");
        } else {
            System.out.println("No hay ningun cliente con ese id");
        }
    }

    private void usersMenu() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- USUARIOS ---");
            System.out.println("1. Anadir");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por id");
            System.out.println("4. Modificar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            opcion = readInt("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        addUser();
                        break;
                    case 2:
                        listUsers();
                        break;
                    case 3:
                        findUser();
                        break;
                    case 4:
                        updateUser();
                        break;
                    case 5:
                        deleteUser();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Esa opcion no existe, prueba otra");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void addUser() throws SQLException {
        System.out.print("Nombre: ");
        String nombre = entrada.nextLine();
        System.out.print("Email: ");
        String email = entrada.nextLine();
        System.out.print("Rol (ADMIN/ORIENTADOR/SECRETARIA/COMERCIAL): ");
        String rol = entrada.nextLine();
        System.out.print("Password: ");
        String passwordHash = "hash_" + entrada.nextLine();
        Usuario u = new Usuario(0, nombre, email, rol, passwordHash);
        if (usuarioService.add(u)) {
            System.out.println("Usuario creado (id " + u.getId() + ")");
        } else {
            System.out.println("Datos incorrectos, revisa el nombre y el rol");
        }
    }

    private void listUsers() throws SQLException {
        List<Usuario> lista = usuarioService.list();
        if (lista.isEmpty()) {
            System.out.println("Todavia no hay usuarios");
            return;
        }
        System.out.printf("%-4s %-20s %-28s %-12s%n", "ID", "NOMBRE", "EMAIL", "ROL");
        System.out.println("--------------------------------------------------------------------");
        for (Usuario u : lista) {
            System.out.printf("%-4d %-20s %-28s %-12s%n",
                    u.getId(), u.getName(), u.getEmail(), u.getRole());
        }
    }

    private void findUser() throws SQLException {
        int id = readInt("Id: ");
        Usuario u = usuarioService.find(id);
        if (u == null) {
            System.out.println("Ese usuario no existe");
        } else {
            System.out.println(u);
        }
    }

    private void updateUser() throws SQLException {
        int id = readInt("Id: ");
        Usuario u = usuarioService.find(id);
        if (u == null) {
            System.out.println("Ese usuario no existe");
            return;
        }
        System.out.print("Nuevo nombre: ");
        u.setName(entrada.nextLine());
        System.out.print("Nuevo email: ");
        u.setEmail(entrada.nextLine());
        System.out.print("Nuevo rol: ");
        u.setRole(entrada.nextLine());
        if (usuarioService.modify(u)) {
            System.out.println("Usuario actualizado");
        } else {
            System.out.println("No se ha podido guardar");
        }
    }

    private void deleteUser() throws SQLException {
        int id = readInt("Id: ");
        if (usuarioService.remove(id)) {
            System.out.println("Usuario borrado");
        } else {
            System.out.println("Ese usuario no existe");
        }
    }

    private void productsMenu() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- PRODUCTOS ---");
            System.out.println("1. Anadir");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por id");
            System.out.println("4. Modificar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            opcion = readInt("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        addProduct();
                        break;
                    case 2:
                        listProducts();
                        break;
                    case 3:
                        findProduct();
                        break;
                    case 4:
                        updateProduct();
                        break;
                    case 5:
                        deleteProduct();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Esa opcion no existe, prueba otra");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void addProduct() throws SQLException {
        System.out.print("Nombre: ");
        String nombre = entrada.nextLine();
        System.out.print("Descripcion: ");
        String desc = entrada.nextLine();
        double precio = readDouble("Precio: ");
        System.out.print("Categoria (GRADO_MEDIO/GRADO_SUPERIOR/ESPECIALIZACION/CURSO_CORTO/IDIOMAS): ");
        String categoria = entrada.nextLine();
        Producto p = new Producto(0, nombre, desc, precio, categoria);
        if (productoService.add(p)) {
            System.out.println("Curso anadido (id " + p.getId() + ")");
        } else {
            System.out.println("Datos incorrectos");
        }
    }

    private void listProducts() throws SQLException {
        List<Producto> lista = productoService.list();
        if (lista.isEmpty()) {
            System.out.println("Todavia no hay cursos");
            return;
        }
        System.out.printf("%-4s %-45s %-18s %-10s%n", "ID", "NOMBRE", "CATEGORIA", "PRECIO");
        System.out.println("--------------------------------------------------------------------------------");
        for (Producto p : lista) {
            System.out.printf("%-4d %-45s %-18s %-10.2f%n",
                    p.getId(), p.getName(), p.getCategory(), p.getPrice());
        }
    }

    private void findProduct() throws SQLException {
        int id = readInt("Id: ");
        Producto p = productoService.find(id);
        if (p == null) {
            System.out.println("No hay ningun curso con ese id");
        } else {
            System.out.println(p);
        }
    }

    private void updateProduct() throws SQLException {
        int id = readInt("Id: ");
        Producto p = productoService.find(id);
        if (p == null) {
            System.out.println("No hay ningun curso con ese id");
            return;
        }
        System.out.print("Nuevo nombre: ");
        p.setName(entrada.nextLine());
        System.out.print("Nueva descripcion: ");
        p.setDescription(entrada.nextLine());
        p.setPrice(readDouble("Nuevo precio: "));
        System.out.print("Nueva categoria: ");
        p.setCategory(entrada.nextLine());
        if (productoService.modify(p)) {
            System.out.println("Curso actualizado");
        } else {
            System.out.println("No se ha podido guardar");
        }
    }

    private void deleteProduct() throws SQLException {
        int id = readInt("Id: ");
        if (productoService.remove(id)) {
            System.out.println("Curso borrado");
        } else {
            System.out.println("No hay ningun curso con ese id");
        }
    }

    private void salesMenu() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- VENTAS ---");
            System.out.println("1. Anadir");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por id");
            System.out.println("4. Modificar");
            System.out.println("5. Eliminar");
            System.out.println("6. Matricular alumno (transaccion: venta + cursos)");
            System.out.println("0. Volver");
            opcion = readInt("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        addSale();
                        break;
                    case 2:
                        listSales();
                        break;
                    case 3:
                        findSale();
                        break;
                    case 4:
                        updateSale();
                        break;
                    case 5:
                        deleteSale();
                        break;
                    case 6:
                        enrollStudent();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Esa opcion no existe, prueba otra");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void addSale() throws SQLException {
        int clienteId = readInt("Id cliente: ");
        int usuarioId = readInt("Id usuario: ");
        LocalDate fecha = readDate("Fecha (yyyy-MM-dd, vacio = hoy): ");
        System.out.print("Estado (PENDIENTE/CONFIRMADA/PAGADA/CANCELADA): ");
        String estado = entrada.nextLine();
        double total = readDouble("Total: ");
        Venta v = new Venta(0, clienteId, usuarioId, fecha, estado, total);
        if (ventaService.add(v)) {
            System.out.println("Matricula guardada (id " + v.getId() + ")");
        } else {
            System.out.println("Datos incorrectos");
        }
    }

    private void listSales() throws SQLException {
        List<Venta> lista = ventaService.list();
        if (lista.isEmpty()) {
            System.out.println("Todavia no hay matriculas");
            return;
        }
        System.out.printf("%-4s %-10s %-10s %-12s %-12s %-10s%n", "ID", "CLIENTE", "USUARIO", "FECHA", "ESTADO", "TOTAL");
        System.out.println("------------------------------------------------------------------------");
        for (Venta v : lista) {
            System.out.printf("%-4d %-10d %-10d %-12s %-12s %-10.2f%n",
                    v.getId(), v.getClientId(), v.getUserId(), v.getDate(), v.getStatus(), v.getTotal());
        }
    }

    private void findSale() throws SQLException {
        int id = readInt("Id: ");
        Venta v = ventaService.find(id);
        if (v == null) {
            System.out.println("No hay ninguna matricula con ese id");
        } else {
            System.out.println(v);
        }
    }

    private void updateSale() throws SQLException {
        int id = readInt("Id: ");
        Venta v = ventaService.find(id);
        if (v == null) {
            System.out.println("No hay ninguna matricula con ese id");
            return;
        }
        v.setClientId(readInt("Nuevo id cliente: "));
        v.setUserId(readInt("Nuevo id usuario: "));
        v.setDate(readDate("Nueva fecha (yyyy-MM-dd, vacio = hoy): "));
        System.out.print("Nuevo estado: ");
        v.setStatus(entrada.nextLine());
        v.setTotal(readDouble("Nuevo total: "));
        if (ventaService.modify(v)) {
            System.out.println("Matricula actualizada");
        } else {
            System.out.println("No se ha podido guardar");
        }
    }

    private void deleteSale() throws SQLException {
        int id = readInt("Id: ");
        if (ventaService.remove(id)) {
            System.out.println("Matricula borrada");
        } else {
            System.out.println("No hay ninguna matricula con ese id");
        }
    }

    private void enrollStudent() {
        System.out.println();
        System.out.println(">>> MATRICULAR ALUMNO <<<");
        int clienteId = readInt("Id del alumno: ");
        int usuarioId = readInt("Id del comercial/orientador: ");

        List<DetalleVenta> lineas = new ArrayList<>();
        boolean anadirMas = true;
        while (anadirMas) {
            int productoId = readInt("Id del curso: ");
            int cantidad = readInt("Cantidad (normalmente 1): ");
            DetalleVenta linea = new DetalleVenta();
            linea.setProductId(productoId);
            linea.setQuantity(cantidad);
            lineas.add(linea);
            System.out.print("Anadir otro curso? (s/n): ");
            anadirMas = entrada.nextLine().trim().equalsIgnoreCase("s");
        }

        try {
            Venta venta = matriculaService.enroll(clienteId, usuarioId, lineas);
            System.out.println("Matricula creada con exito:");
            System.out.println("  " + venta);
            System.out.println("  Total: " + venta.getTotal() + " EUR");
            System.out.println("  Lineas: " + lineas.size());
        } catch (EntidadNoEncontradaException e) {
            System.out.println("No se pudo matricular: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error en la base de datos (se ha hecho rollback): " + e.getMessage());
        }
    }

    private void saleDetailsMenu() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- DETALLES DE VENTA ---");
            System.out.println("1. Anadir");
            System.out.println("2. Listar todos");
            System.out.println("3. Listar por id de venta");
            System.out.println("4. Buscar por id");
            System.out.println("5. Modificar");
            System.out.println("6. Eliminar");
            System.out.println("0. Volver");
            opcion = readInt("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        addDetail();
                        break;
                    case 2:
                        listDetails();
                        break;
                    case 3:
                        listDetailsBySale();
                        break;
                    case 4:
                        findDetail();
                        break;
                    case 5:
                        updateDetail();
                        break;
                    case 6:
                        deleteDetail();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Esa opcion no existe, prueba otra");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void addDetail() throws SQLException {
        int ventaId = readInt("Id venta: ");
        int productoId = readInt("Id producto: ");
        int cantidad = readInt("Cantidad: ");
        double precio = readDouble("Precio unitario: ");
        DetalleVenta d = new DetalleVenta(0, ventaId, productoId, cantidad, precio);
        if (detalleService.add(d)) {
            System.out.println("Linea guardada (id " + d.getId() + ")");
        } else {
            System.out.println("Datos incorrectos");
        }
    }

    private void listDetails() throws SQLException {
        List<DetalleVenta> lista = detalleService.list();
        if (lista.isEmpty()) {
            System.out.println("Todavia no hay lineas");
            return;
        }
        System.out.printf("%-4s %-8s %-10s %-10s %-12s%n", "ID", "VENTA", "PRODUCTO", "CANTIDAD", "PRECIO");
        System.out.println("----------------------------------------------------");
        for (DetalleVenta d : lista) {
            System.out.printf("%-4d %-8d %-10d %-10d %-12.2f%n",
                    d.getId(), d.getSaleId(), d.getProductId(), d.getQuantity(), d.getUnitPrice());
        }
    }

    private void listDetailsBySale() throws SQLException {
        int ventaId = readInt("Id de la venta: ");
        List<DetalleVenta> lista = detalleService.listBySale(ventaId);
        if (lista.isEmpty()) {
            System.out.println("Sin lineas para esa venta");
            return;
        }
        System.out.printf("%-4s %-8s %-10s %-10s %-12s%n", "ID", "VENTA", "PRODUCTO", "CANTIDAD", "PRECIO");
        System.out.println("----------------------------------------------------");
        for (DetalleVenta d : lista) {
            System.out.printf("%-4d %-8d %-10d %-10d %-12.2f%n",
                    d.getId(), d.getSaleId(), d.getProductId(), d.getQuantity(), d.getUnitPrice());
        }
    }

    private void findDetail() throws SQLException {
        int id = readInt("Id: ");
        DetalleVenta d = detalleService.find(id);
        if (d == null) {
            System.out.println("No hay ninguna linea con ese id");
        } else {
            System.out.println(d);
        }
    }

    private void updateDetail() throws SQLException {
        int id = readInt("Id: ");
        DetalleVenta d = detalleService.find(id);
        if (d == null) {
            System.out.println("No hay ninguna linea con ese id");
            return;
        }
        d.setSaleId(readInt("Nuevo id venta: "));
        d.setProductId(readInt("Nuevo id producto: "));
        d.setQuantity(readInt("Nueva cantidad: "));
        d.setUnitPrice(readDouble("Nuevo precio unitario: "));
        if (detalleService.modify(d)) {
            System.out.println("Linea actualizada");
        } else {
            System.out.println("No se ha podido guardar");
        }
    }

    private void deleteDetail() throws SQLException {
        int id = readInt("Id: ");
        if (detalleService.remove(id)) {
            System.out.println("Linea borrada");
        } else {
            System.out.println("No hay ninguna linea con ese id");
        }
    }

    private void reportsMenu() {
        int opcion;
        do {
            System.out.println();
            System.out.println("--- INFORMES ---");
            System.out.println("1. Facturacion total");
            System.out.println("2. Matriculas pendientes de pago");
            System.out.println("3. Matriculas pagadas");
            System.out.println("4. Ciclos formativos");
            System.out.println("5. Comerciales y matriculas que gestionan");
            System.out.println("6. Matriculas de un alumno");
            System.out.println("7. Marcar matricula como pagada");
            System.out.println("0. Volver");
            opcion = readInt("Opcion: ");
            try {
                switch (opcion) {
                    case 1:
                        revenueReport();
                        break;
                    case 2:
                        statusReport("PENDIENTE");
                        break;
                    case 3:
                        statusReport("PAGADA");
                        break;
                    case 4:
                        cyclesReport();
                        break;
                    case 5:
                        salespeopleReport();
                        break;
                    case 6:
                        studentEnrollmentsReport();
                        break;
                    case 7:
                        markAsPaidAction();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Esa opcion no existe, prueba otra");
                }
            } catch (SQLException e) {
                System.out.println("Error en la base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void revenueReport() throws SQLException {
        double total = ventaService.totalRevenue();
        System.out.println("Facturacion total (pagadas + confirmadas): " + total + " EUR");
    }

    private void statusReport(String estado) throws SQLException {
        List<Venta> lista;
        if (estado.equals("PENDIENTE")) {
            lista = ventaService.listPendingPayment();
        } else {
            lista = ventaService.listPaid();
        }
        if (lista.isEmpty()) {
            System.out.println("No hay matriculas en estado " + estado);
            return;
        }
        for (Venta v : lista) {
            System.out.println("  " + v + " | curso " + v.getAcademicYear());
        }
    }

    private void cyclesReport() throws SQLException {
        List<Producto> ciclos = productoService.listCycles();
        if (ciclos.isEmpty()) {
            System.out.println("No hay ciclos");
            return;
        }
        for (Producto p : ciclos) {
            String tipo;
            if (p.isMediumCycle()) {
                tipo = "[GM]";
            } else {
                tipo = "[GS]";
            }
            System.out.println("  " + tipo + " " + p.getName() + " - " + p.getPrice() + " EUR");
        }
    }

    private void salespeopleReport() throws SQLException {
        List<Usuario> comerciales = usuarioService.listSalespeople();
        if (comerciales.isEmpty()) {
            System.out.println("No hay comerciales");
            return;
        }
        for (Usuario u : comerciales) {
            int n = ventaService.countEnrollmentsManagedBy(u.getId());
            System.out.println("  " + u.getName() + " (id " + u.getId() + ") -> " + n + " matricula(s)");
        }
    }

    private void studentEnrollmentsReport() throws SQLException {
        int clienteId = readInt("Id del alumno: ");
        Cliente c = clienteService.find(clienteId);
        if (c == null) {
            System.out.println("Alumno no encontrado");
            return;
        }
        System.out.println("Alumno: " + c.getName() + " (" + c.getEmail() + ")");
        List<Venta> matriculas = ventaService.enrollmentsOf(clienteId);
        if (matriculas.isEmpty()) {
            System.out.println("Este alumno no tiene matriculas");
            return;
        }
        for (Venta v : matriculas) {
            System.out.println("  " + v + " | curso " + v.getAcademicYear());
        }
    }

    private void markAsPaidAction() throws SQLException {
        int id = readInt("Id de la matricula a marcar como pagada: ");
        if (ventaService.markAsPaid(id)) {
            System.out.println("Matricula " + id + " marcada como PAGADA");
        } else {
            System.out.println("No se ha podido actualizar (id no existe)");
        }
    }

    private void exportMenu() {
        System.out.println();
        System.out.println(" EXPORTAR A CSV ");
        System.out.println("1. Exportar clientes");
        System.out.println("2. Exportar ventas");
        System.out.println("0. Volver");
        int opcion = readInt("Opcion: ");
        try {
            if (opcion == 1) {
                ExportadorCSV.exportarClientes(clienteService.list(), "clientes.csv");
                System.out.println("Exportado a clientes.csv");
            } else if (opcion == 2) {
                ExportadorCSV.exportarVentas(ventaService.list(), "ventas.csv");
                System.out.println("Exportado a ventas.csv");
            }
        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error escribiendo el fichero: " + e.getMessage());
        }
    }

    private int readInt(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Pon un numero entero porfa");
            }
        }
    }

    private double readDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(entrada.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Eso no es un numero");
            }
        }
    }

    private LocalDate readDate(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();
            if (texto.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("La fecha tiene que ser asi: yyyy-MM-dd");
            }
        }
    }
}