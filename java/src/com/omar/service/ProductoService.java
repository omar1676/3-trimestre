package com.omar.service;

import com.omar.entities.Producto;
import com.omar.repository.ProductoRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    private final ProductoRepository repository;

    private static final String[] CATEGORIAS_VALIDAS = {
            "GRADO_MEDIO", "GRADO_SUPERIOR", "ESPECIALIZACION",
            "CURSO_CORTO", "IDIOMAS"
    };

    public ProductoService() throws SQLException {
        this.repository = new ProductoRepository();
    }

    public boolean add(Producto producto) throws SQLException {
        if (producto.getName() == null || producto.getName().trim().isEmpty()) {
            return false;
        }
        if (producto.getPrice() < 0) {
            return false;
        }
        if (!isValidCategory(producto.getCategory())) {
            return false;
        }
        repository.create(producto);
        return true;
    }

    public Producto find(int id) throws SQLException {
        return repository.findById(id);
    }

    public List<Producto> list() throws SQLException {
        return repository.findAll();
    }

    public boolean modify(Producto producto) throws SQLException {
        if (repository.findById(producto.getId()) == null) {
            return false;
        }
        if (!isValidCategory(producto.getCategory())) {
            return false;
        }
        return repository.update(producto);
    }

    public boolean remove(int id) throws SQLException {
        return repository.delete(id);
    }

    public boolean isValidCategory(String categoria) {
        if (categoria == null) {
            return false;
        }
        for (String c : CATEGORIAS_VALIDAS) {
            if (c.equals(categoria)) {
                return true;
            }
        }
        return false;
    }

    public List<Producto> listByCategory(String categoria) throws SQLException {
        return repository.findByCategory(categoria);
    }

    public List<Producto> listCycles() throws SQLException {
        List<Producto> ciclos = new ArrayList<>();
        ciclos.addAll(repository.findByCategory("GRADO_MEDIO"));
        ciclos.addAll(repository.findByCategory("GRADO_SUPERIOR"));
        return ciclos;
    }

    public List<Producto> listComplementaryOffer() throws SQLException {
        List<Producto> oferta = new ArrayList<>();
        oferta.addAll(repository.findByCategory("CURSO_CORTO"));
        oferta.addAll(repository.findByCategory("IDIOMAS"));
        oferta.addAll(repository.findByCategory("ESPECIALIZACION"));
        return oferta;
    }
}