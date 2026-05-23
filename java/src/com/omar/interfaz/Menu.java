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

