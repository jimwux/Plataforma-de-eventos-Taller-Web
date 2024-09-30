INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

TRUNCATE TABLE evento;

INSERT INTO evento (lugar, fecha, imagenUrl, nombre, ciudad_id)
VALUES
    ('Teatro el Nacional Sancor Seguros', '2024-10-05', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/somos-las-chicas-de-la-culpa.jpeg', 'Somos Las Chicas de la Culpa', 1),  -- La Plata
    ('Estadio Velez Sarsfield', '2024-12-19', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/parense-de-manos-2.jpg', 'Parense de Manos II', 3),  -- Tigre
    ('Teatro Gran Rex', '2024-10-05', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/siames-live-in-buenos-aires.jpg', 'Siamés live in Buenos Aires', 2),  -- Mar del Plata
    ('Teatro Universidad', '2024-10-12', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/aventuras-perrunas.jpg', 'Aventuras Perrunas', 4),  -- Chaco
    ('Multiespacio 880', '2024-12-14', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/san-telmo-tango-show.jpg', 'San Telmo Tango Show', 6),  -- Córdoba
    ('Teatro Stella Maris', '2024-09-15', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/las-aventuras-de-mario.jpg', 'Las aventuras de Mario', 5),  -- Trelew
    ('Doha Boliche', '2024-09-27', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/salvaje-fest.jpeg', 'Salvaje Fest', 1),  -- La Plata
    ('Hipodromo de San Isidro', '2025-03-21', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/lollapalooza-2025.jpg', 'Lollapalooza 2025', 3),  -- Tigre
    ('Teatro Astral', '2024-11-09', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/antigona-en-el-baño.jpg', 'Antigona en el baño', 2),  -- Mar del Plata
    ('Predio de la Estación Belgrano', '2024-10-12', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/harlem-festival.jpg', 'Harlem Festival', 4);  -- Chaco

