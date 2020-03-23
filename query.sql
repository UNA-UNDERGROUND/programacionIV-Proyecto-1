CREATE SCHEMA banco;

CREATE TABLE cliente (
  cedula int NOT NULL PRIMARY KEY,
  nombre varchar(255) NOT NULL,
  apellidos varchar(255) NOT NULL,
  telefono varchar(100) NOT NULL
);

CREATE TABLE usuario (
  cedula int NOT NULL PRIMARY KEY,
  pass varchar(8) NOT NULL
);

CREATE TABLE cuenta (
  id_cuenta int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cedula int NOT NULL,
  codigo varchar(3) NOT NULL,
  saldo decimal(15, 4) NOT NULL,
  limite_transferencia int(11) NOT NULL,
  CONSTRAINT FK_cuenta_cedula 
    FOREIGN KEY (cedula) 
    REFERENCES cliente(cedula)
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
  moneda varchar(3) not null,
  monto decimal(13, 4) not null,
  descripcion varchar(255) not null,
  fecha_deposito datetime default current_timestamp,
  CONSTRAINT FK_movimiento_cuenta
    FOREIGN KEY (id_cuenta)
    REFERENCES cuenta(id_cuenta),
  CONSTRAINT FK_movimiento_moneda
    FOREIGN KEY (moneda)
    REFERENCES moneda(codigo)
);
