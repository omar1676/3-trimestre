SET SERVEROUTPUT ON;

DECLARE
    TYPE t_nombre IS TABLE OF VARCHAR2(75);
    TYPE t_email  IS TABLE OF VARCHAR2(120);
    v_nombres t_nombre := t_nombre('Marta Ortiz','Hugo Salas','Nerea Gil','Ivan Mora','Sara Pena');
    v_emails  t_email  := t_email('marta.ortiz@estudiante.xtart.com','hugo.salas@estudiante.xtart.com','nerea.gil@estudiante.xtart.com','ivan.mora@estudiante.xtart.com','sara.pena@estudiante.xtart.com');
    v_mod NUMBER;
    v_del NUMBER;
BEGIN
    FOR i IN 1 .. v_nombres.COUNT LOOP
        INSERT INTO clientes (nombre, email, telefono, direccion)
        VALUES (v_nombres(i), v_emails(i), '600000' || i, 'Direccion de prueba ' || i);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('clientes creados: ' || v_nombres.COUNT);

    v_mod := 0;
    FOR i IN 1 .. v_emails.COUNT LOOP
        UPDATE clientes SET telefono = '611111' || i WHERE email = v_emails(i);
        v_mod := v_mod + SQL%ROWCOUNT;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('clientes modificados: ' || v_mod);

    v_del := 0;
    FOR i IN 1 .. v_emails.COUNT LOOP
        DELETE FROM clientes WHERE email = v_emails(i);
        v_del := v_del + SQL%ROWCOUNT;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('clientes eliminados: ' || v_del);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('error en clientes: ' || SQLERRM);
END;
/

DECLARE
    TYPE t_txt IS TABLE OF VARCHAR2(120);
    v_nombres t_txt := t_txt('Test Uno','Test Dos','Test Tres','Test Cuatro','Test Cinco');
    v_emails  t_txt := t_txt('test1@xtart.com','test2@xtart.com','test3@xtart.com','test4@xtart.com','test5@xtart.com');
    v_mod NUMBER;
    v_del NUMBER;
BEGIN
    FOR i IN 1 .. v_nombres.COUNT LOOP
        INSERT INTO usuarios (nombre, email, rol, password_hash)
        VALUES (v_nombres(i), v_emails(i), 'COMERCIAL', 'hash_test');
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('usuarios creados: ' || v_nombres.COUNT);

    v_mod := 0;
    FOR i IN 1 .. v_emails.COUNT LOOP
        UPDATE usuarios SET rol = 'SECRETARIA' WHERE email = v_emails(i);
        v_mod := v_mod + SQL%ROWCOUNT;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('usuarios modificados: ' || v_mod);

    v_del := 0;
    FOR i IN 1 .. v_emails.COUNT LOOP
        DELETE FROM usuarios WHERE email = v_emails(i);
        v_del := v_del + SQL%ROWCOUNT;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('usuarios eliminados: ' || v_del);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('error en usuarios: ' || SQLERRM);
END;
/

DECLARE
    TYPE t_txt IS TABLE OF VARCHAR2(50);
    v_nombres t_txt := t_txt('Curso Test A','Curso Test B','Curso Test C','Curso Test D','Curso Test E');
    v_precio NUMBER := 100;
BEGIN
    FOR i IN 1 .. v_nombres.COUNT LOOP
        INSERT INTO productos (nombre, descripcion, precio, categoria)
        VALUES (v_nombres(i), 'curso de prueba', v_precio + i*10, 'CURSO_CORTO');
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('productos creados: ' || v_nombres.COUNT);

    UPDATE productos SET precio = precio * 1.10 WHERE nombre LIKE 'Curso Test%';
    DBMS_OUTPUT.PUT_LINE('productos modificados: ' || SQL%ROWCOUNT);

    DELETE FROM productos WHERE nombre LIKE 'Curso Test%';
    DBMS_OUTPUT.PUT_LINE('productos eliminados: ' || SQL%ROWCOUNT);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('error en productos: ' || SQLERRM);
END;
/

DECLARE
    v_cliente NUMBER;
    v_usuario NUMBER;
    v_ids SYS.ODCINUMBERLIST := SYS.ODCINUMBERLIST();
    v_id NUMBER;
BEGIN
    SELECT MIN(id) INTO v_cliente FROM clientes;
    SELECT MIN(id) INTO v_usuario FROM usuarios;

    FOR i IN 1 .. 5 LOOP
        INSERT INTO ventas (cliente_id, usuario_id, fecha, estado, total)
        VALUES (v_cliente, v_usuario, SYSDATE, 'PENDIENTE', 100 * i)
        RETURNING id INTO v_id;
        v_ids.EXTEND;
        v_ids(v_ids.COUNT) := v_id;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('ventas creadas: ' || v_ids.COUNT);

    FOR i IN 1 .. v_ids.COUNT LOOP
        UPDATE ventas SET estado = 'CONFIRMADA' WHERE id = v_ids(i);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('ventas modificadas: ' || v_ids.COUNT);

    FOR i IN 1 .. v_ids.COUNT LOOP
        DELETE FROM ventas WHERE id = v_ids(i);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('ventas eliminadas: ' || v_ids.COUNT);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('error en ventas: ' || SQLERRM);
END;
/

DECLARE
    v_venta NUMBER;
    v_producto NUMBER;
    v_ids SYS.ODCINUMBERLIST := SYS.ODCINUMBERLIST();
    v_id NUMBER;
BEGIN
    SELECT MIN(id) INTO v_venta FROM ventas;
    SELECT MIN(id) INTO v_producto FROM productos;

    FOR i IN 1 .. 5 LOOP
        INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario)
        VALUES (v_venta, v_producto, i, 50 * i)
        RETURNING id INTO v_id;
        v_ids.EXTEND;
        v_ids(v_ids.COUNT) := v_id;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('detalles creados: ' || v_ids.COUNT);

    FOR i IN 1 .. v_ids.COUNT LOOP
        UPDATE detalle_venta SET cantidad = cantidad + 1 WHERE id = v_ids(i);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('detalles modificados: ' || v_ids.COUNT);

    FOR i IN 1 .. v_ids.COUNT LOOP
        DELETE FROM detalle_venta WHERE id = v_ids(i);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('detalles eliminados: ' || v_ids.COUNT);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('error en detalle_venta: ' || SQLERRM);
END;
/
