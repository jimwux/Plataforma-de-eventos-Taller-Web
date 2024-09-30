INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

DROP TABLE entrada;

TRUNCATE TABLE evento;

INSERT INTO evento (lugar, fecha, imagenUrl, nombre, categoria)
VALUES
    ('Teatro el Nacional Sancor Seguros', '2024-10-05', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/somos-las-chicas-de-la-culpa.jpeg', 'Somos Las Chicas de la Culpa', 'concierto'),
    ('Estadio Velez Sarsfield', '2024-12-19', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/parense-de-manos-2.jpg', 'Parense de Manos II', 'concierto'),
    ('Teatro Gran Rex', '2024-10-05', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/siames-live-in-buenos-aires.jpg', 'Siamés live in Buenos Aires', 'otro'),
    ('Teatro Universidad', '2024-10-12', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/aventuras-perrunas.jpg', 'Aventuras Perrunas', 'familiar'),
    ('Multiespacio 880', '2024-12-14', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/san-telmo-tango-show.jpg', 'San Telmo Tango Show', 'convencion'),
    ('Teatro Stella Maris', '2024-09-15', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/las-aventuras-de-mario.jpg', 'Las aventuras de Mario', 'otro'),
    ('Doha Boliche', '2024-09-27', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/salvaje-fest.jpeg', 'Salvaje Fest', 'convencion'),
    ('Hipodromo de San Isidro', '2025-03-21', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/lollapalooza-2025.jpg', 'Lollapalooza 2025', 'deporte'),
    ('Teatro Astral', '2024-11-09', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/antigona-en-el-baño.jpg', 'Antigona en el baño', 'otro'),
    ('Predio de la Estación Belgrano', '2024-10-12', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/harlem-festival.jpg', 'Harlem Festival', 'otro');


