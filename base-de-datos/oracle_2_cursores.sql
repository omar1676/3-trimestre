SET SERVEROUTPUT ON;

-- CLIENTES

BEGIN
    FOR c IN (SELECT id, nombre, email FROM clientes ORDER BY id) LOOP
        DBMS_OUTPUT.PUT_LINE(c.id || ' - ' || c.nombre || ' - ' || c.email);
    END LOOP;
END;
/

BEGIN
    FOR c IN (SELECT nombre, telefono FROM clientes WHERE direccion LIKE '%Madrid%') LOOP
        DBMS_OUTPUT.PUT_LINE('Cliente de Madrid: ' || c.nombre || ' tel ' || c.telefono);
    END LOOP;
END;
/

DECLARE
    contador NUMBER := 0;
BEGIN
    FOR c IN (SELECT nombre FROM clientes) LOOP
        contador := contador + 1;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Numero total de clientes: ' || contador);
END;
/

BEGIN
    FOR c IN (SELECT nombre, email FROM clientes ORDER BY nombre ASC) LOOP
        DBMS_OUTPUT.PUT_LINE(c.nombre || ' - ' || c.email);
    END LOOP;
END;
/

DECLARE
    CURSOR cur_clientes IS SELECT id, nombre FROM clientes WHERE id <= 5;
    v_id clientes.id%TYPE;
    v_nombre clientes.nombre%TYPE;
BEGIN
    OPEN cur_clientes;
    LOOP
        FETCH cur_clientes INTO v_id, v_nombre;
        EXIT WHEN cur_clientes%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Primeros clientes: ' || v_id || ' ' || v_nombre);
    END LOOP;
    CLOSE cur_clientes;
END;
/

-- USUARIOS

BEGIN
    FOR u IN (SELECT id, nombre, rol FROM usuarios ORDER BY id) LOOP
        DBMS_OUTPUT.PUT_LINE(u.id || ' - ' || u.nombre || ' - ' || u.rol);
    END LOOP;
END;
/

BEGIN
    FOR u IN (SELECT nombre, email FROM usuarios WHERE rol = 'COMERCIAL') LOOP
        DBMS_OUTPUT.PUT_LINE('Comercial: ' || u.nombre || ' - ' || u.email);
    END LOOP;
END;
/

DECLARE
    total NUMBER := 0;
BEGIN
    FOR u IN (SELECT id FROM usuarios) LOOP
        total := total + 1;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Total de personal: ' || total);
END;
/

BEGIN
    FOR u IN (SELECT rol, COUNT(*) AS cuantos FROM usuarios GROUP BY rol) LOOP
        DBMS_OUTPUT.PUT_LINE('Rol ' || u.rol || ': ' || u.cuantos || ' personas');
    END LOOP;
END;
/

DECLARE
    CURSOR cur_admin IS SELECT nombre FROM usuarios WHERE rol = 'ADMIN';
    v_nombre usuarios.nombre%TYPE;
BEGIN
    OPEN cur_admin;
    LOOP
        FETCH cur_admin INTO v_nombre;
        EXIT WHEN cur_admin%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Administrador: ' || v_nombre);
    END LOOP;
    CLOSE cur_admin;
END;
/

-- PRODUCTOS

BEGIN
    FOR p IN (SELECT id, nombre, precio FROM productos ORDER BY id) LOOP
        DBMS_OUTPUT.PUT_LINE(p.id || ' - ' || p.nombre || ' - ' || p.precio || ' euros');
    END LOOP;
END;
/

BEGIN
    FOR p IN (SELECT nombre, precio FROM productos WHERE precio > 800) LOOP
        DBMS_OUTPUT.PUT_LINE('Curso caro: ' || p.nombre || ' (' || p.precio || ')');
    END LOOP;
END;
/

BEGIN
    FOR p IN (SELECT categoria, COUNT(*) AS cuantos FROM productos GROUP BY categoria) LOOP
        DBMS_OUTPUT.PUT_LINE('Categoria ' || p.categoria || ': ' || p.cuantos || ' cursos');
    END LOOP;
END;
/

DECLARE
    suma NUMBER := 0;
BEGIN
    FOR p IN (SELECT precio FROM productos) LOOP
        suma := suma + p.precio;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Suma de todos los precios: ' || suma);
END;
/

DECLARE
    CURSOR cur_idiomas IS SELECT nombre, precio FROM productos WHERE categoria = 'IDIOMAS';
    v_nombre productos.nombre%TYPE;
    v_precio productos.precio%TYPE;
BEGIN
    OPEN cur_idiomas;
    LOOP
        FETCH cur_idiomas INTO v_nombre, v_precio;
        EXIT WHEN cur_idiomas%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Idioma: ' || v_nombre || ' - ' || v_precio);
    END LOOP;
    CLOSE cur_idiomas;
END;
/

-- VENTAS

BEGIN
    FOR v IN (SELECT id, fecha, estado, total FROM ventas ORDER BY id) LOOP
        DBMS_OUTPUT.PUT_LINE('Venta ' || v.id || ' - ' || v.estado || ' - ' || v.total || ' euros');
    END LOOP;
END;
/

BEGIN
    FOR v IN (SELECT id, total FROM ventas WHERE estado = 'PAGADA') LOOP
        DBMS_OUTPUT.PUT_LINE('Venta pagada ' || v.id || ': ' || v.total);
    END LOOP;
END;
/

DECLARE
    suma NUMBER := 0;
BEGIN
    FOR v IN (SELECT total FROM ventas WHERE estado = 'PAGADA') LOOP
        suma := suma + v.total;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Facturacion total pagada: ' || suma || ' euros');
END;
/

BEGIN
    FOR v IN (SELECT estado, COUNT(*) AS cuantas FROM ventas GROUP BY estado) LOOP
        DBMS_OUTPUT.PUT_LINE('Estado ' || v.estado || ': ' || v.cuantas || ' matriculas');
    END LOOP;
END;
/

DECLARE
    CURSOR cur_pendientes IS SELECT id, total FROM ventas WHERE estado = 'PENDIENTE';
    v_id ventas.id%TYPE;
    v_total ventas.total%TYPE;
BEGIN
    OPEN cur_pendientes;
    LOOP
        FETCH cur_pendientes INTO v_id, v_total;
        EXIT WHEN cur_pendientes%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Matricula pendiente ' || v_id || ': ' || v_total);
    END LOOP;
    CLOSE cur_pendientes;
END;
/

-- DETALLE_VENTA

BEGIN
    FOR d IN (SELECT id, venta_id, producto_id, cantidad FROM detalle_venta ORDER BY id) LOOP
        DBMS_OUTPUT.PUT_LINE('Detalle ' || d.id || ' - venta ' || d.venta_id || ' - curso ' || d.producto_id || ' x' || d.cantidad);
    END LOOP;
END;
/

BEGIN
    FOR d IN (SELECT venta_id, precio_unitario FROM detalle_venta WHERE precio_unitario > 1000) LOOP
        DBMS_OUTPUT.PUT_LINE('Linea cara en venta ' || d.venta_id || ': ' || d.precio_unitario);
    END LOOP;
END;
/

DECLARE
    suma NUMBER := 0;
BEGIN
    FOR d IN (SELECT cantidad, precio_unitario FROM detalle_venta) LOOP
        suma := suma + (d.cantidad * d.precio_unitario);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Importe total de todas las lineas: ' || suma);
END;
/

BEGIN
    FOR d IN (SELECT producto_id, COUNT(*) AS veces FROM detalle_venta GROUP BY producto_id) LOOP
        DBMS_OUTPUT.PUT_LINE('Curso ' || d.producto_id || ' aparece en ' || d.veces || ' matriculas');
    END LOOP;
END;
/

DECLARE
    CURSOR cur_detalle IS SELECT venta_id, cantidad, precio_unitario FROM detalle_venta WHERE venta_id = 3;
    v_venta detalle_venta.venta_id%TYPE;
    v_cant detalle_venta.cantidad%TYPE;
    v_precio detalle_venta.precio_unitario%TYPE;
BEGIN
    OPEN cur_detalle;
    LOOP
        FETCH cur_detalle INTO v_venta, v_cant, v_precio;
        EXIT WHEN cur_detalle%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Venta 3 incluye curso x' || v_cant || ' a ' || v_precio);
    END LOOP;
    CLOSE cur_detalle;
END;
/
