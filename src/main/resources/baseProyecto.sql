INSERT INTO provincia (nombre)
VALUES
    ('Buenos Aires'),
    ('Santa Fe');

INSERT INTO ciudad (nombre, provincia_id)
VALUES
    ('Ciudad de Buenos Aires',1),
    ('La Matanza', 1),
    ('San Isidro', 1),
    ('Santa Fe', 2);

INSERT INTO evento (lugar, fecha, imagenUrl, nombre, acercaDe, instagramUrl, direccionUrl, ciudad_id, categoria)
VALUES
    ('Teatro el Nacional Sancor Seguros', '2024-10-05', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/somos-las-chicas-de-la-culpa.jpeg', 'Somos Las Chicas de la Culpa', 'Todas las funciones son distintas y delirantes! No es una obra de teatro, no es stand up, SOMOS Las Chicas de la Culpa, una juntada con tus amigas más zarpadas!', 'https://www.instagram.com/laschicasdelaculpa/?hl=es-la', 'https://maps.app.goo.gl/d5AYyTHtKkNqT4AG7', 1, 'teatro'), -- Ciudad de Buenos Aires
    ('Estadio Velez Sarsfield', '2024-12-19', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/parense-de-manos-2.jpg', 'Parense de Manos II', 'Se trata de un evento de boxeo y entretenimiento, que reunirá a 20 celebridades del mundo del streaming, redes sociales y creadores de contenido, que se subirán al ring en unas épicas peleas, junto a increíbles shows de música que se realizarán a lo largo de la noche.', 'https://www.instagram.com/parensedemanosok/?hl=es-la', 'https://maps.app.goo.gl/pSb5FusMe5Gdai528', 1, 'convencion'), -- Ciudad de Buenos Aires
    ('Teatro Gran Rex', '2024-10-05', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/siames-live-in-buenos-aires.jpg', 'Siamés live in Buenos Aires', 'La música de Siames mezcla rock alternativo con tintes de pop y electrónica. Su sonido es característico, con guitarras potentes y letras que llegan al corazón. Tienen una onda que atrapa desde el primer acorde y no suelta hasta el último.', 'https://www.instagram.com/siamesmusic/?hl=es', 'https://maps.app.goo.gl/yKQkZk3tgVraAQnA8', 1, 'concierto'), -- Ciudad de Buenos Aires
    ('Teatro Universidad', '2024-10-12', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/aventuras-perrunas.jpg', 'Aventuras Perrunas', 'Unos divertidos perritos muy coloridos junto a Luz, una alegre vecina, te invitan a pasar una tarde llena de risas y juegos, en donde el aprendizaje y el valor de la familia son lo más importante.', 'https://www.instagram.com/teatrouniversidad/?hl=es', 'https://maps.app.goo.gl/z1Rs9v9NtLLhRUVs5', 2, 'familia'), -- La Matanza
    ('Multiespacio 880', '2024-12-14', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/san-telmo-tango-show.jpg', 'San Telmo Tango Show', 'Déjate cautivar por los ritmos y las historias que han dado vida al tango. Cada escena está cargada de emoción, pasión y tradición, transportándote al corazón de Buenos Aires.', 'https://www.instagram.com/multiespacio.880/', 'https://maps.app.goo.gl/xR3Xoyh7qMXDcBkq5', 1, 'teatro'), -- Ciudad de Buenos Aires
    ('Teatro Stella Maris', '2024-09-15', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/las-aventuras-de-mario.jpg', 'Las aventuras de Mario', 'Nuestro Héroe en esta gran aventura por encontrar a su hermano conocerá a la Princesa y sus amigos, quiénes unirán sus fuerzas para rescatar a la estrella mágica y salvar al reino encantado.', 'https://www.instagram.com/teatrostellamaris/?hl=es', 'https://maps.app.goo.gl/jM21WH3FZA5ixaks6', 3, 'familia'), -- San Isidro
    ('Doha Boliche', '2024-09-27', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/salvaje-fest.jpeg', 'Salvaje Fest', 'Un festival que sacará tu habilidad más SALVAJE. Conéctate con el entorno para enfrentar un recorrido exigente, con tramos de pavimento y destapado. Habrá segmentos cronometrados, puntos de hidratación, apoyo mecánico y al final comida, música y cerveza al estilo SALVAJE.', 'https://www.instagram.com/salvajegravelfest/', 'https://maps.app.goo.gl/DhbqGfn3Q7KMKaZB9', 1, 'deporte'), -- Ciudad de Buenos Aires
    ('Hipodromo de San Isidro', '2025-03-21', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/lollapalooza-2025.jpg', 'Lollapalooza 2025', 'Lo que sucede en #LollaAR no tiene comparación: 3 días con más de 100 artistas, en 5 escenarios llenos de variedad para que todos disfruten. Expresiones artísticas, actividades infantiles, oferta gastronómica para todos los gustos y una locación de ensueño.', 'https://www.instagram.com/lollapaloozaar/?hl=es', 'https://maps.app.goo.gl/FnLSnhrGbTX7YNND6', 3, 'concierto'), -- San Isidro
    ('Teatro Astral', '2024-11-09', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/antigona-en-el-baño.jpg', 'Antigona en el baño', 'Ignacia (Verónica Llinás) es una estrella que está por salir a escena tras un largo exilio en TV. A la edad y la decadencia se suman miedos. Sus aliados: el hijo de su representante (Esteban Lamothe) y un "coach ontológico" (Héctor Díaz). ¿Será suficiente? El humor lo dirá.', 'https://www.instagram.com/antigonaobra/?hl=es', 'https://maps.app.goo.gl/sQTQNygCbqXsqKyTA', 1, 'teatro'), -- Ciudad de Buenos Aires
    ('Predio de la Estación Belgrano', '2024-10-12', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/harlem-festival.jpg', 'Harlem Festival', 'El Harlem sigue creciendo en cada edición, reuniendo a miles de jóvenes, amigos y familias en Santa Fe con mega producción, escenarios y actividades. Desde su inicio en 2018, se posicionó como uno de los festivales más importantes de Argentina, con artistas como Wos, Duki y Nicki Nicole en el line-up.', 'https://www.instagram.com/harlemfestival/?hl=es', 'https://maps.app.goo.gl/3VDUqmNEeVvgSAZPA', 4, 'convencion'); -- Santa Fe


INSERT INTO entrada (nombre, precio, evento_id)
VALUES
    ('General', 5000, 1),
    ('VIP', 15000, 1),
    ('Palco', 18000, 1),
    ('Preferencial', 20000, 1),

    ('General', 7000, 2),
    ('Platea', 13500, 2),
    ('Preferencial', 17500, 2),
    ('Palco', 19000),
    ('VIP', 21000, 2),
    ('Ultra VIP', 29000, 2),

    ('Económica', 12000,3),
    ('Platea Baja', 14000,3),
    ('Balcón', 18000,3),
    ('Grada', 18300,3),
    ('Lateral', 18500,3),
    ('Frontal', 18750,3),
    ('Box', 38000,3),

    ('General', 6500,4),
    ('Entrada Familiar', 13000,4),

    ('General', 9600,5),
    ('Asiento VIP', 15000,5),
    ('Entrada Especial', 17000, 5),
    ('Entrada Familiar', 19300, 5),

    ('General', 6500,6),
    ('Entrada Familiar', 13000,6),
    ('Palco VIP', 19500,6),


    ('General', 15600,7),
    ('VIP Platino', 20000,7),
    ('Preferencial Plus', 24320,7),

    ('Entrada Estándar', 31000,8),
    ('Zona Fan', 35000,8),
    ('Entrada Preferencial', 36000,8),
    ('Entrada VIP', 40000,8),
    ('Fila 1', 42000,8),

    ('General', 13500,9),
    ('Balcón VIP', 16500, 9),
    ('Palco', 18000, 9),

    ('Entrada Estándar', 17000,10),
    ('Entrada Preferencial', 19500,10),
    ('Entrada VIP', 21200,10);
