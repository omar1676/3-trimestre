-- CRM Instituto Xtart - Oracle PL/SQL
-- 10 procedimientos + 10 funciones (2+2 por tabla) y vistas

-- ============================================================
-- CLIENTES
-- ============================================================

CREATE OR REPLACE PROCEDURE insertar_cliente(
    p_nombre IN VARCHAR2,
    p_email IN VARCHAR2,
    p_telefono IN VARCHAR2,
    p_direccion IN VARCHAR2
) IS
    v_existe NUMBER;
BEGIN
    -- validacion: nombre y email no pueden venir vacios
    IF p_nombre IS NULL OR p_email IS NULL THEN
        DBMS_OUTPUT.PUT_LINE('nombre y email son obligatorios');
        RETURN;
    END IF;
    -- control: no permitir email duplicado
    SELECT COUNT(*) INTO v_existe FROM clientes WHERE email = p_email;
    IF v_existe > 0 THEN
        DBMS_OUTPUT.PUT_LINE('ya existe un cliente con ese email');
        RETURN;
    END IF;
    INSERT INTO clientes (nombre, email, telefono, direccion)
    VALUES (p_nombre, p_email, p_telefono, p_direccion);
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('cliente anadido');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('error al insertar cliente: ' || SQLERRM);
END;
/

CREATE OR REPLACE PROCEDURE mostrar_cliente(p_id IN NUMBER) IS
    v_nombre clientes.nombre%TYPE;
    v_email clientes.email%TYPE;
BEGIN
    SELECT nombre, email INTO v_nombre, v_email
    FROM clientes WHERE id = p_id;
    DBMS_OUTPUT.PUT_LINE(v_nombre || ' - ' || v_email);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('no hay cliente con ese id');
END;
/

CREATE OR REPLACE FUNCTION contar_clientes RETURN NUMBER IS
    v_total NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_total FROM clientes;
    RETURN v_total;
END;
/

CREATE OR REPLACE FUNCTION nombre_cliente(p_id IN NUMBER) RETURN VARCHAR2 IS
    v_nombre clientes.nombre%TYPE;
BEGIN
    SELECT nombre INTO v_nombre FROM clientes WHERE id = p_id;
    RETURN v_nombre;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'no encontrado';
END;
/

-- ============================================================
-- USUARIOS
-- ============================================================

CREATE OR REPLACE PROCEDURE insertar_usuario(
    p_nombre IN VARCHAR2,
    p_email IN VARCHAR2,
    p_rol IN VARCHAR2
) IS
    v_existe NUMBER;
BEGIN
    IF p_nombre IS NULL OR p_email IS NULL THEN
        DBMS_OUTPUT.PUT_LINE('nombre y email son obligatorios');
        RETURN;
    END IF;
    SELECT COUNT(*) INTO v_existe FROM usuarios WHERE email = p_email;
    IF v_existe > 0 THEN
        DBMS_OUTPUT.PUT_LINE('ya existe un usuario con ese email');
        RETURN;
    END IF;
    INSERT INTO usuarios (nombre, email, rol, password_hash)
    VALUES (p_nombre, p_email, p_rol, 'sin_hash');
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('usuario anadido');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('error al insertar usuario: ' || SQLERRM);
END;
/

CREATE OR REPLACE PROCEDURE cambiar_rol(p_id IN NUMBER, p_rol IN VARCHAR2) IS
BEGIN
    UPDATE usuarios SET rol = p_rol WHERE id = p_id;
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('no existe ese usuario');
    ELSE
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('rol cambiado');
    END IF;
END;
/

CREATE OR REPLACE FUNCTION contar_usuarios RETURN NUMBER IS
    v_total NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_total FROM usuarios;
    RETURN v_total;
END;
/

CREATE OR REPLACE FUNCTION rol_usuario(p_id IN NUMBER) RETURN VARCHAR2 IS
    v_rol usuarios.rol%TYPE;
BEGIN
    SELECT rol INTO v_rol FROM usuarios WHERE id = p_id;
    RETURN v_rol;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'no encontrado';
END;
/

-- ============================================================
-- PRODUCTOS
-- ============================================================

CREATE OR REPLACE PROCEDURE insertar_producto(
    p_nombre IN VARCHAR2,
    p_descripcion IN VARCHAR2,
    p_precio IN NUMBER,
    p_categoria IN VARCHAR2
) IS
BEGIN
    IF p_nombre IS NULL OR p_precio IS NULL THEN
        DBMS_OUTPUT.PUT_LINE('nombre y precio son obligatorios');
        RETURN;
    END IF;
    IF p_precio < 0 THEN
        DBMS_OUTPUT.PUT_LINE('el precio no puede ser negativo');
        RETURN;
    END IF;
    INSERT INTO productos (nombre, descripcion, precio, categoria)
    VALUES (p_nombre, p_descripcion, p_precio, p_categoria);
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('curso anadido');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('error al insertar curso: ' || SQLERRM);
END;
/

CREATE OR REPLACE PROCEDURE subir_precio(p_id IN NUMBER, p_porcentaje IN NUMBER) IS
    v_precio productos.precio%TYPE;
BEGIN
    SELECT precio INTO v_precio FROM productos WHERE id = p_id;
    v_precio := v_precio + (v_precio * p_porcentaje / 100);
    UPDATE productos SET precio = v_precio WHERE id = p_id;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('nuevo precio: ' || v_precio);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('no existe ese curso');
END;
/

CREATE OR REPLACE FUNCTION precio_producto(p_id IN NUMBER) RETURN NUMBER IS
    v_precio productos.precio%TYPE;
BEGIN
    SELECT precio INTO v_precio FROM productos WHERE id = p_id;
    RETURN v_precio;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
END;
/

CREATE OR REPLACE FUNCTION clasificar_precio(p_precio IN NUMBER) RETURN VARCHAR2 IS
    v_texto VARCHAR2(20);
BEGIN
    IF p_precio >= 1000 THEN
        v_texto := 'caro';
    ELSIF p_precio >= 500 THEN
        v_texto := 'medio';
    ELSE
        v_texto := 'barato';
    END IF;
    RETURN v_texto;
END;
/

-- ============================================================
-- VENTAS
-- ============================================================

CREATE OR REPLACE PROCEDURE cambiar_estado_venta(p_id IN NUMBER, p_estado IN VARCHAR2) IS
BEGIN
    UPDATE ventas SET estado = p_estado WHERE id = p_id;
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('no existe esa matricula');
    ELSE
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('estado actualizado a ' || p_estado);
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE listar_ventas_cliente(p_cliente_id IN NUMBER) IS
BEGIN
    FOR v IN (SELECT id, fecha, estado, total FROM ventas WHERE cliente_id = p_cliente_id) LOOP
        DBMS_OUTPUT.PUT_LINE('matricula ' || v.id || ' - ' || v.estado || ' - ' || v.total);
    END LOOP;
END;
/

CREATE OR REPLACE FUNCTION facturacion_total RETURN NUMBER IS
    v_suma NUMBER := 0;
BEGIN
    SELECT SUM(total) INTO v_suma FROM ventas WHERE estado = 'PAGADA';
    RETURN NVL(v_suma, 0);
END;
/

CREATE OR REPLACE FUNCTION contar_ventas_estado(p_estado IN VARCHAR2) RETURN NUMBER IS
    v_total NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_total FROM ventas WHERE estado = p_estado;
    RETURN v_total;
END;
/

-- ============================================================
-- DETALLE_VENTA
-- ============================================================

CREATE OR REPLACE PROCEDURE insertar_detalle(
    p_venta_id IN NUMBER,
    p_producto_id IN NUMBER,
    p_cantidad IN NUMBER,
    p_precio IN NUMBER
) IS
    v_existe_venta NUMBER;
    v_existe_prod NUMBER;
BEGIN
    -- control: la venta y el producto deben existir
    SELECT COUNT(*) INTO v_existe_venta FROM ventas WHERE id = p_venta_id;
    SELECT COUNT(*) INTO v_existe_prod FROM productos WHERE id = p_producto_id;
    IF v_existe_venta = 0 THEN
        DBMS_OUTPUT.PUT_LINE('no existe esa venta');
        RETURN;
    END IF;
    IF v_existe_prod = 0 THEN
        DBMS_OUTPUT.PUT_LINE('no existe ese curso');
        RETURN;
    END IF;
    INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario)
    VALUES (p_venta_id, p_producto_id, p_cantidad, p_precio);
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('linea anadida');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('error al insertar linea: ' || SQLERRM);
END;
/

CREATE OR REPLACE PROCEDURE mostrar_detalles_venta(p_venta_id IN NUMBER) IS
    v_cont NUMBER := 0;
BEGIN
    FOR d IN (SELECT producto_id, cantidad, precio_unitario FROM detalle_venta WHERE venta_id = p_venta_id) LOOP
        v_cont := v_cont + 1;
        DBMS_OUTPUT.PUT_LINE('curso ' || d.producto_id || ' x' || d.cantidad || ' a ' || d.precio_unitario);
    END LOOP;
    IF v_cont = 0 THEN
        DBMS_OUTPUT.PUT_LINE('esa matricula no tiene lineas');
    END IF;
END;
/

CREATE OR REPLACE FUNCTION total_linea(p_id IN NUMBER) RETURN NUMBER IS
    v_cantidad detalle_venta.cantidad%TYPE;
    v_precio detalle_venta.precio_unitario%TYPE;
BEGIN
    SELECT cantidad, precio_unitario INTO v_cantidad, v_precio
    FROM detalle_venta WHERE id = p_id;
    RETURN v_cantidad * v_precio;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
END;
/

CREATE OR REPLACE FUNCTION contar_lineas_venta(p_venta_id IN NUMBER) RETURN NUMBER IS
    v_total NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_total FROM detalle_venta WHERE venta_id = p_venta_id;
    RETURN v_total;
END;
/

