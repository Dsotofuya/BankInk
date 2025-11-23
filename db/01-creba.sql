drop table if exists TRANSACCIONES;
drop table if exists PRODUCTOS;
drop table if exists TARJETAS;


CREATE TABLE PRODUCTOS (
  ID_PRODUCTO SERIAL primary key,
  PRIMER_NOMBRE VARCHAR(32) not null,
  SEGUNDO_NOMBRE VARCHAR(32),
  PRIMER_APELLIDO VARCHAR(32) not null,
  SEGUNDO_APELLIDO VARCHAR(32)
);

SELECT setval(pg_get_serial_sequence('productos','id_producto'), 100000, true);

INSERT INTO PRODUCTOS(PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO) 
VALUES ('Daniel', 'Leonardo', 'Soto', 'Fuya'),
('Carlos',  'Andrés',  'Ramírez',    'Gómez'),
  ('María',   'Lucía',   'Fernández',  'Torres'),
  ('José',    'Miguel',  'Hernández',  'López'),
  ('Ana',     'Isabel',  'Martínez',   'Sánchez'),
  ('Luis',    'Eduardo', 'Vargas',     'Pérez') RETURNING id_producto;


comment on table PRODUCTOS is 'Tabla que contiene los productos bancarios';
comment on column PRODUCTOS.ID_PRODUCTO is 'Id del producto';
comment on column PRODUCTOS.PRIMER_NOMBRE is 'Primer nombre del propietario';
comment on column PRODUCTOS.SEGUNDO_NOMBRE is 'Segundo nombre del propietario';
comment on column PRODUCTOS.PRIMER_APELLIDO is 'Primer apellido del propietario';
comment on column PRODUCTOS.SEGUNDO_APELLIDO is 'Segundo apellido del propietario';



create table TARJETAS(
	ID_TARJETA SERIAL primary key,
	NRO_TARJETA VARCHAR(16) not null,
	NOMBRE_TITULAR VARCHAR(128) not null,
	FECHA_VENCIMIENTO_TARJETA DATE not null default now(),
	ESTADO_TARJETA CHAR not null check (ESTADO_TARJETA IN ('A'  , 'I', 'B' )),
	DIVISA_TARJETA VARCHAR(3) not null default 'USD',
	BALANCE_TARJETA BIGINT default 0
);

comment on table TARJETAS is 'Tabla que contiene las tarjetas debito/credito';
comment on column TARJETAS.ID_TARJETA is 'ID de tarjeta';
comment on column TARJETAS.NRO_TARJETA is 'Numero de la tarjeta';
comment on column TARJETAS.NOMBRE_TITULAR is 'Nombre del titular de la tarjeta';
comment on column TARJETAS.FECHA_VENCIMIENTO_TARJETA is 'Fecha de vencimiento de la tarjeta';
comment on column TARJETAS.ESTADO_TARJETA is 'Estado de la tarjeta (A: Activa, I: Inactiva, B: Bloqueada)';
comment on column TARJETAS.DIVISA_TARJETA is 'Divisa de la tarjeta';
comment on column TARJETAS.BALANCE_TARJETA is 'Balance o saldo en la tarjeta en centavos';

alter table TARJETAS add constraint unique_numero_tarjeta
    unique (NRO_TARJETA);

create table TRANSACCIONES(
	ID_TRANSACCION SERIAL primary key,
	NRO_TARJETA_TRANSACCION VARCHAR(16) not null,
	MONTO_TRANSACCION BIGINT not null,
	FECHA_TRANSACCION TIMESTAMP default now(), 
	DIVISA_TRANSACCION VARCHAR(3) not null,
	ESTADO_TRANSACCION CHAR not null check (ESTADO_TRANSACCION IN ('E'  , 'A' )),
	FOREIGN KEY (NRO_TARJETA_TRANSACCION) REFERENCES TARJETAS(NRO_TARJETA)
);

comment on table TRANSACCIONES is 'Tabla que contiene las transacciones de las tarjetas';
comment on column TRANSACCIONES.ID_TRANSACCION is 'ID de la transaccion';
comment on column TRANSACCIONES.NRO_TARJETA_TRANSACCION is 'Numero de tarjeta asociado a la transaccion';
comment on column TRANSACCIONES.MONTO_TRANSACCION is 'Monto de la transaccion en centavos';
comment on column TRANSACCIONES.FECHA_TRANSACCION is 'Fecha en la que se realiza la transaccion';
comment on column TRANSACCIONES.DIVISA_TRANSACCION is 'Divisa en la que se realiza la transaccion';
comment on column TRANSACCIONES.ESTADO_TRANSACCION is 'Estado de la transaccion (E: Exitosa, A: Anulada)';



commit;


