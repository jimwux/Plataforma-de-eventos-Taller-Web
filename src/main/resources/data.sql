INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

TRUNCATE TABLE evento;
INSERT INTO evento (lugar, fecha, imagenUrl, nombre)
VALUES
    ('Teatro el Nacional Sancor Seguros', '2024-10-05', 'https://drive.google.com/file/d/1j2Dd4SSvJkzFXkxyftQWbSirsGF29kJ5/view?usp=drive_link', 'Somos Las Chicas de la Culpa'),
    ('Estadio Velez Sarsfield', '2024-12-19', 'https://drive.google.com/file/d/1QJulilpn3k6d2hhfEwIBJcllz8oeIOvl/view?usp=drive_link', 'Parense de Manos II'),
    ('Teatro Gran Rex', '2024-10-05', 'https://drive.google.com/file/d/1x9FOSrj6TWWkobQf8E2dguVwKod8npO3/view?usp=drive_link', 'Siamés live in Buenos Aires'),
    ('Teatro Universidad', '2024-10-12', 'https://drive.google.com/file/d/1H4C-GjE8_oGtrEC-lLNak7YgInCYHrwM/view?usp=drive_link', 'Aventuras Perrunas'),
    ('Multiespacio 880', '2024-12-14', 'https://drive.google.com/file/d/1ZujZdKxaBlrlf4XZBad9aA53m3uRZzPi/view?usp=drive_link', 'San Telmo Tango Show'),
    ('Teatro Stella Maris', '2024-09-15', 'https://drive.google.com/file/d/1NCLVonBJ4B3D-Swpd6hh4A2_3BvtIL77/view?usp=drive_link', 'Las aventuras de Mario - Rescatando a la estrella magica'),
    ('Doha Boliche', '2024-09-27', 'https://drive.google.com/file/d/1UIJRADjmopFESJ457pOtAZVpBGpD5Z5a/view?usp=drive_link', 'Salvaje Fest'),
    ('Hipodromo de San Isidro', '2025-03-21', 'https://drive.google.com/file/d/12kG5Lki3CZR0MC6wG5FWuDxwu6-4lfzq/view?usp=drive_link', 'Lollapalooza 2025'),
    ('Teatro Astral', '2024-11-09', 'https://drive.google.com/file/d/1-G7TNcKI_7NGyKB3GUk_DwwJWSaezO3s/view?usp=drive_link', 'Antigona en el baño'),
    ('Predio de la Estación Belgrano', '2024-10-12', 'https://drive.google.com/file/d/1fyz06pf0Wa1wZ16_4styNT-xEzN4SMV9/view?usp=sharing', 'Harlem Festival');