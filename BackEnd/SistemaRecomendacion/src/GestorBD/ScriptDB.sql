/**
 *
 * @author casca
 */
connect system/manager as sysdba;
 
-- ----------------- USUARIO ----------------- 
create user servidor identified by servidor;

grant all privileges to servidor identified by servidor;

connect servidor/servidor;

-- ----------------- CREACION DE UN CURSOR -----------------
CREATE OR REPLACE PACKAGE types
AS
     TYPE ref_cursor IS REF CURSOR;
END;
/

-- ################################### USUARIOS ###################################
-- ----------------- TABLA DE USUARIO -----------------
CREATE TABLE Usuario(
    username VARCHAR(100),
    nombre VARCHAR(100),
    email VARCHAR(100),
    clave VARCHAR(100),
    rol int,
    CONSTRAINTS pkUsuario PRIMARY KEY (username)
);

-- ----------------- INSERTAR USUARIO ----------------- 
CREATE OR REPLACE PROCEDURE insertarUsuario(username IN Usuario.username%TYPE, nombre IN Usuario.nombre%TYPE, email IN Usuario.email%TYPE, clave IN Usuario.clave%TYPE, rol IN Usuario.rol%TYPE)
AS
BEGIN
	INSERT INTO Usuario VALUES(username,nombre,email,clave,rol);
END;
/

-- ----------------- ACTUALIZAR USUARIO ----------------- 
CREATE OR REPLACE PROCEDURE actualizarUsuario(usernamein IN Usuario.username%TYPE, nombrein IN Usuario.nombre%TYPE, emailin IN Usuario.email%TYPE,clavein IN Usuario.clave%TYPE, rolin IN Usuario.rol%TYPE)
AS
BEGIN
	UPDATE Usuario SET nombre=nombrein,email=emailin,clave=clavein,rol=rolin WHERE username=usernamein;
END;
/

-- ----------------- BUSCAR USUARIO ----------------- 
CREATE OR REPLACE FUNCTION buscarUsuario(usernamein IN Usuario.username%TYPE)
RETURN Types.ref_cursor 
AS 
        USUARIO_cursor types.ref_cursor; 
BEGIN 
  OPEN USUARIO_cursor FOR 
       SELECT * FROM Usuario WHERE username=usernamein; 
RETURN USUARIO_cursor; 
END;
/

-- ----------------- LISTAR USUARIO ----------------- 
CREATE OR REPLACE FUNCTION listarUsuario
RETURN Types.ref_cursor 
AS 
        USUARIO_cursor types.ref_cursor; 
BEGIN 
  OPEN USUARIO_cursor FOR 
       SELECT * FROM Usuario ; 
RETURN USUARIO_cursor; 
END;
/

-- ----------------- ELIMINAR USUARIO ----------------- 
create or replace procedure eliminarUsuario(usernamein IN Usuario.username%TYPE)
as
begin
    delete from Usuario where username=usernamein;
end;
/

-- ################################### PRODUCTOS ###################################
-- ----------------- TABLA DE PRODUCTO -----------------
CREATE TABLE Product(
    id int,
    title VARCHAR(100),
    shortdesc VARCHAR(100),
    cantidad int,
    price int,
    image int,
    CONSTRAINTS pkProduct PRIMARY KEY (id)
);

-- ----------------- INSERTAR PRODUCTO ----------------- 
CREATE OR REPLACE PROCEDURE insertarProduct(id IN Product.id%TYPE, title IN Product.title%TYPE, shortdesc IN Product.shortdesc%TYPE, cantidad IN Product.cantidad%TYPE, price IN Product.price%TYPE, image IN Product.image%TYPE)
AS
BEGIN
	INSERT INTO Product VALUES(id,title,shortdesc,cantidad,price,image);
END;
/

-- ----------------- ACTUALIZAR PRODUCTO ----------------- 
CREATE OR REPLACE PROCEDURE actualizarProduct(idin IN Product.id%TYPE, titlein IN Product.title%TYPE, shortdescin IN Product.shortdesc%TYPE,cantidadin IN Product.cantidad%TYPE, pricein IN Product.price%TYPE, imagein IN Product.image%TYPE)
AS
BEGIN
	UPDATE Product SET title=titlein,shortdesc=shortdescin,cantidad=cantidadin,price=pricein,image=imagein WHERE id=idin;
END;
/

-- ----------------- BUSCAR PRODUCTO ----------------- 
CREATE OR REPLACE FUNCTION buscarProduct(titlein IN Product.title%TYPE)
RETURN Types.ref_cursor 
AS 
        producto_cursor types.ref_cursor; 
BEGIN 
  OPEN producto_cursor FOR 
       SELECT * FROM Product WHERE title=titlein; 
RETURN producto_cursor; 
END;
/

-- ----------------- LISTAR PRODUCTO ----------------- 
CREATE OR REPLACE FUNCTION listarProduct
RETURN Types.ref_cursor 
AS 
        producto_cursor types.ref_cursor; 
BEGIN 
  OPEN producto_cursor FOR 
       SELECT * FROM Product ; 
RETURN producto_cursor; 
END;
/

-- ----------------- ELIMINAR PRODUCTO ----------------- 
create or replace procedure eliminarProduct(idin IN Product.id%TYPE)
as
begin
    delete from Product where id=idin;
end;
/

-- ----------------- TOTAL A PAGAR ----------------- 
CREATE OR REPLACE FUNCTION totalPagar(price IN INT, cantidad IN INT)
RETURN NUMBER
AS          
BEGIN 
    RETURN price*cantidad;
END;
/


-- #############################################################################

-- PRUEBAS NO EJECUTAR

-- -----------------  DROPS -----------------
DROP PROCEDURE insertarUsuario;
DROP PROCEDURE actualizarUsuario;
DROP FUNCTION buscarUsuario;
DROP FUNCTION listarUsuario;
DROP PROCEDURE eliminarUsuario;
DROP TABLE Usuario;

DROP PROCEDURE insertarProduct;
DROP PROCEDURE actualizarProduct;
DROP FUNCTION buscarProduct;
DROP FUNCTION listarProduct;
DROP PROCEDURE eliminarProduct;
DROP TABLE Product;

DROP PACKAGE types;
DROP USER servidor CASCADE; 

DECLARE
BEGIN
    insertarProduct();
END;
/

-- #############################################################################