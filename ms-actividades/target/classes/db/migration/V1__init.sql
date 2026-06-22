CREATE TABLE actividades (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_actividad VARCHAR(100),
    descripcion VARCHAR(255),
    calendario VARCHAR(100),
    socio_id BIGINT
);

INSERT INTO actividades
(nombre_actividad, descripcion, calendario, socio_id)
VALUES
    ('Taller de Pintura', 'Actividad recreativa', 'Sábado 15:00', 1);

INSERT INTO actividades
(nombre_actividad, descripcion, calendario, socio_id)
VALUES
    ('Cabalgata Familiar', 'Recorrido turístico rural', 'Domingo 10:00', 1);

INSERT INTO actividades
(nombre_actividad, descripcion, calendario, socio_id)
VALUES
    ('Cocina Campesina', 'Preparación de comidas típicas', 'Viernes 18:00', 2);

INSERT INTO actividades
(nombre_actividad, descripcion, calendario, socio_id)
VALUES
    ('Huerto Comunitario', 'Actividad agrícola educativa', 'Miércoles 09:00', 3);

INSERT INTO actividades
(nombre_actividad, descripcion, calendario, socio_id)
VALUES
    ('Taller de Artesanía', 'Creación de productos artesanales', 'Sábado 11:00', 2);

COMMIT;