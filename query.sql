CREATE SCHEMA banco;

CREATE TABLE cliente (
  cedula int NOT NULL PRIMARY KEY,
  nombre varchar(255) NOT NULL,
  apellidos varchar(255) NOT NULL,
  telefono varchar(100) NOT NULL
);

CREATE TABLE usuario (
  cedula int NOT NULL PRIMARY KEY,
  pass varchar(8) NOT NULL,
  CONSTRAINT FK_usuario_cedula
    FOREIGN KEY (cedula)
    REFERENCES cliente(cedula)
);

CREATE TABLE administrador (
  cedula int NOT NULL PRIMARY KEY,
  pass varchar(8) NOT NULL
);

CREATE TABLE cuenta (
  id_cuenta int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cedula int NOT NULL,
  moneda varchar(3) NOT NULL,
  saldo decimal(15, 4) NOT NULL,
  limite_transferencia int(11) NOT NULL,
  CONSTRAINT FK_cuenta_cedula
    FOREIGN KEY (cedula)
    REFERENCES cliente(cedula),
  CONSTRAINT FK_cuenta_moneda
    FOREIGN KEY (moneda)
    REFERENCES moneda(codigo)
);

CREATE TABLE moneda (
  codigo varchar(3) NOT NULL PRIMARY KEY,
  nombre varchar(100) NOT NULL,
  signo varchar(5) NOT NULL,
  precio_compra decimal(13, 4) NOT NULL,
  porcentaje_interes float NOT NULL
);

CREATE TABLE movimiento(
  id_transaccion int not null AUTO_INCREMENT PRIMARY KEY,
  id_cuenta int not null,
  deposito boolean not null,
  monto decimal(13, 4) not null,
  descripcion varchar(255) not null,
  fecha_deposito datetime default current_timestamp,
  movimiento_caja boolean default false,
  CONSTRAINT FK_movimiento_cuenta
    FOREIGN KEY (id_cuenta)
    REFERENCES cuenta(id_cuenta)
);


CREATE TABLE cuenta_vinculada (
  id_cuenta int NOT NULL,
  cedula int NOT NULL,
    CONSTRAINT FK_cuenta_vinculada_id_cuenta
    FOREIGN KEY (id_cuenta)
    REFERENCES cuenta(id_cuenta),
  CONSTRAINT FK_cuenta_vinculada_cedula
    FOREIGN KEY (cedula)
    REFERENCES cliente(cedula)
);

INSERT INTO moneda (codigo, nombre, signo, precio_compra, porcentaje_interes)
VALUES 
('CRC','Colon Costarricense' ,'₡' ,'1'       ,'10'),
('CAD','Dolar Canadiense'    ,'C$','402,1500','8'),
('EUR','Euro'                ,'€' ,'612,61'  ,'12,5'),
('JPY','Yen'                 ,'¥' ,'5,28'    ,'9,6'),
('RUB','Rublo Ruso'          ,'₽' ,'7,6'     ,'10'),
('USD','Dolar Estadounidense','$' ,'566,63'  ,'5'),