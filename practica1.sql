drop schema dam2tm06uf2p1;
create schema dam2tm06uf2p1;
use dam2tm06uf2p1;

create table jugador (
	id int primary key auto_increment,
    usuario varchar(100),
    password varchar(100),
    nombre varchar(100),
    partidas int,
    ganadas int
);

create table carta (
	id int primary key auto_increment,
    id_jugador int,
    numero varchar(100),
    color varchar(20),
    constraint carta_jugador foreign key (id_jugador) references jugador (id)
);

create table partida (
	id int primary key auto_increment,
    id_carta int,
	constraint partida_carta foreign key (id_carta) references carta (id)
);

insert into jugador (usuario,password,nombre) values ('gaizkang', 'nugi42', 'Gaizka');
insert into jugador (usuario,password,nombre) values ('plastelina', 'microsueno', 'Jaume');

