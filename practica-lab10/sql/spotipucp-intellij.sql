-- Versión para IntelliJ/DataGrip (sin 'delimiter'). En MySQL Workbench use spotipucp.sql
create database if not exists spotipucp;
use spotipucp;

drop table if exists playlist_cancion;
drop table if exists playlist;
drop table if exists cancion;
drop table if exists usuario;

create table usuario (
    id int auto_increment primary key,
    codigo varchar(20) not null unique,
    nombre varchar(100) not null,
    correo varchar(120) not null unique
);

create table cancion (
    id int auto_increment primary key,
    titulo varchar(150) not null,
    artista varchar(100) not null,
    genero varchar(50) not null,
    duracion_segundos int not null
);

create table playlist (
    id int auto_increment primary key,
    id_usuario int not null,
    nombre varchar(120) not null,
    fecha_creacion date not null,
    constraint fk_playlist_usuario foreign key (id_usuario) references usuario(id)
);

create table playlist_cancion (
    id int auto_increment primary key,
    id_playlist int not null,
    id_cancion int not null,
    orden int not null,
    constraint fk_playlist_cancion_playlist foreign key (id_playlist) references playlist(id),
    constraint fk_playlist_cancion_cancion foreign key (id_cancion) references cancion(id),
    constraint uq_playlist_cancion unique (id_playlist, id_cancion),
    constraint uq_playlist_orden unique (id_playlist, orden)
);

drop procedure if exists buscar_usuario_por_id;
drop procedure if exists listar_usuarios;
drop procedure if exists listar_canciones;
drop procedure if exists insertar_playlist;
drop procedure if exists listar_playlists;
drop procedure if exists insertar_playlist_cancion;
drop procedure if exists listar_canciones_por_playlist;


create procedure buscar_usuario_por_id(in p_id int)
begin
    select id, codigo, nombre, correo
    from usuario
    where id = p_id;
end;

create procedure listar_usuarios()
begin
    select id, codigo, nombre, correo
    from usuario
    order by id;
end;

create procedure listar_canciones()
begin
    select id, titulo, artista, genero, duracion_segundos
    from cancion
    order by id;
end;

create procedure insertar_playlist(
    in p_id_usuario int,
    in p_nombre varchar(120),
    in p_fecha_creacion date,
    out p_id int
)
begin
    insert into playlist(id_usuario, nombre, fecha_creacion)
    values(p_id_usuario, p_nombre, p_fecha_creacion);

    set p_id = last_insert_id();
end;

create procedure listar_playlists()
begin
    select p.id, p.id_usuario, p.nombre, p.fecha_creacion,
           u.codigo as codigo_usuario, u.nombre as nombre_usuario, u.correo as correo_usuario
    from playlist p
    inner join usuario u on u.id = p.id_usuario
    order by p.id;
end;

create procedure insertar_playlist_cancion(
    in p_id_playlist int,
    in p_id_cancion int,
    in p_orden int,
    out p_id int
)
begin
    insert into playlist_cancion(id_playlist, id_cancion, orden)
    values(p_id_playlist, p_id_cancion, p_orden);

    set p_id = last_insert_id();
end;

create procedure listar_canciones_por_playlist(in p_id_playlist int)
begin
    select pc.id as id_playlist_cancion, pc.orden,
           c.id, c.titulo, c.artista, c.genero, c.duracion_segundos
    from playlist_cancion pc
    inner join cancion c on c.id = pc.id_cancion
    where pc.id_playlist = p_id_playlist
    order by pc.orden;
end;


insert into usuario(codigo, nombre, correo) values
('2026001', 'Usuario 1', 'usuario1@spotipucp.edu.pe'),
('2026002', 'Usuario 2', 'usuario2@spotipucp.edu.pe'),
('2026003', 'Usuario 3', 'usuario3@spotipucp.edu.pe'),
('2026004', 'Usuario 4', 'usuario4@spotipucp.edu.pe'),
('2026005', 'Usuario 5', 'usuario5@spotipucp.edu.pe'),
('2026006', 'Usuario 6', 'usuario6@spotipucp.edu.pe'),
('2026007', 'Usuario 7', 'usuario7@spotipucp.edu.pe'),
('2026008', 'Usuario 8', 'usuario8@spotipucp.edu.pe'),
('2026009', 'Usuario 9', 'usuario9@spotipucp.edu.pe'),
('2026010', 'Usuario 10', 'usuario10@spotipucp.edu.pe'),
('2026011', 'Usuario 11', 'usuario11@spotipucp.edu.pe'),
('2026012', 'Usuario 12', 'usuario12@spotipucp.edu.pe'),
('2026013', 'Usuario 13', 'usuario13@spotipucp.edu.pe'),
('2026014', 'Usuario 14', 'usuario14@spotipucp.edu.pe'),
('2026015', 'Usuario 15', 'usuario15@spotipucp.edu.pe'),
('2026016', 'Usuario 16', 'usuario16@spotipucp.edu.pe'),
('2026017', 'Usuario 17', 'usuario17@spotipucp.edu.pe'),
('2026018', 'Usuario 18', 'usuario18@spotipucp.edu.pe'),
('2026019', 'Usuario 19', 'usuario19@spotipucp.edu.pe'),
('2026020', 'Usuario 20', 'usuario20@spotipucp.edu.pe');

insert into cancion(titulo, artista, genero, duracion_segundos) values
('Noche de Verano', 'Los Andinos', 'Pop', 210),
('Camino al Sur', 'Marea Criolla', 'Folk', 245),
('Luz de Neon', 'Kilometro Cero', 'Electronica', 198),
('Vuelta al Barrio', 'Zona Norte', 'Reggaeton', 187),
('Silencio Azul', 'Nube Gris', 'Indie', 233),
('Ritmo de Ciudad', 'Los Vecinos', 'Salsa', 256),
('Ola Eterna', 'Costa Brava', 'Pop', 202),
('Cielo Roto', 'Fuego Lento', 'Rock', 271),
('Danza del Sol', 'Tribu Andina', 'Folk', 219),
('Codigo Binario', 'Circuito Digital', 'Electronica', 205),
('Amanecer Gris', 'Marea Baja', 'Indie', 240),
('Fiesta Sin Fin', 'Los del Puerto', 'Cumbia', 188),
('Puente de Cristal', 'Rio Sonoro', 'Pop', 214),
('Latidos de Concreto', 'Zona Norte', 'Rock', 264),
('Bajo la Lluvia', 'Nube Gris', 'Indie', 229),
('Sabor Tropical', 'Los Vecinos', 'Salsa', 249),
('Estrellas de Neon', 'Kilometro Cero', 'Electronica', 196),
('Historias del Barrio', 'Marea Criolla', 'Folk', 231),
('Corazon de Fuego', 'Fuego Lento', 'Rock', 258),
('Paso a Paso', 'Costa Brava', 'Pop', 207),
('Ritmo Andino', 'Tribu Andina', 'Folk', 223),
('Vibra Urbana', 'Zona Norte', 'Reggaeton', 192),
('Ecos del Mar', 'Rio Sonoro', 'Pop', 216),
('Frecuencia Digital', 'Circuito Digital', 'Electronica', 201),
('Noche Sin Estrellas', 'Nube Gris', 'Indie', 236),
('Tambor y Fuego', 'Los del Puerto', 'Cumbia', 194),
('Camino de Regreso', 'Los Andinos', 'Pop', 211),
('Alma de Barrio', 'Marea Criolla', 'Folk', 227),
('Pulso Electrico', 'Kilometro Cero', 'Electronica', 199),
('Bajo el Mismo Cielo', 'Costa Brava', 'Pop', 220),
('Guerreros del Ritmo', 'Los Vecinos', 'Salsa', 253),
('Rio de Fuego', 'Fuego Lento', 'Rock', 267),
('Susurros del Viento', 'Tribu Andina', 'Folk', 225),
('Codigo de Barras', 'Circuito Digital', 'Electronica', 203),
('Marea de Emociones', 'Marea Baja', 'Indie', 238),
('Salsa y Sabor', 'Los del Puerto', 'Cumbia', 191),
('Cristal Roto', 'Rio Sonoro', 'Pop', 213),
('Concreto y Cielo', 'Zona Norte', 'Rock', 262),
('Danza sin Fin', 'Los Andinos', 'Pop', 208),
('Latidos del Sur', 'Marea Criolla', 'Folk', 230);
-- ============================================================
-- Procedimientos adicionales para la práctica de servicios web
-- ============================================================
drop procedure if exists buscar_cancion_por_id;
drop procedure if exists buscar_playlist_por_id;
drop procedure if exists listar_playlists_por_usuario;
drop procedure if exists eliminar_canciones_por_playlist;
drop procedure if exists eliminar_playlist;


create procedure buscar_cancion_por_id(in p_id int)
begin
    select id, titulo, artista, genero, duracion_segundos
    from cancion
    where id = p_id;
end;

create procedure buscar_playlist_por_id(in p_id int)
begin
    select p.id, p.id_usuario, p.nombre, p.fecha_creacion,
           u.codigo as codigo_usuario, u.nombre as nombre_usuario, u.correo as correo_usuario
    from playlist p
    inner join usuario u on u.id = p.id_usuario
    where p.id = p_id;
end;

create procedure listar_playlists_por_usuario(in p_id_usuario int)
begin
    select p.id, p.id_usuario, p.nombre, p.fecha_creacion,
           u.codigo as codigo_usuario, u.nombre as nombre_usuario, u.correo as correo_usuario
    from playlist p
    inner join usuario u on u.id = p.id_usuario
    where p.id_usuario = p_id_usuario
    order by p.id;
end;

create procedure eliminar_canciones_por_playlist(in p_id_playlist int)
begin
    delete from playlist_cancion
    where id_playlist = p_id_playlist;
end;

create procedure eliminar_playlist(in p_id int)
begin
    delete from playlist
    where id = p_id;
end;

