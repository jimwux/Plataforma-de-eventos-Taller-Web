INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

TRUNCATE TABLE evento;

IINSERT INTO evento (lugar, fecha, imagenUrl, nombre, acercaDe, instagramUrl, ciudad_id)
VALUES
    ('Teatro el Nacional Sancor Seguros', '2024-10-05', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/somos-las-chicas-de-la-culpa.jpeg', 'Somos Las Chicas de la Culpa', 'Todas las funciones son distintas y delirantes! No es una obra de teatro, no es stand up, SOMOS Las Chicas de la Culpa, una juntada con tus amigas más zarpadas!', 'https://www.instagram.com/laschicasdelaculpa/?hl=es-la', 1), -- Ciudad de Buenos Aires
    ('Estadio Velez Sarsfield', '2024-12-19', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/parense-de-manos-2.jpg', 'Parense de Manos II', 'Se trata de un evento de boxeo y entretenimiento, que reunirá a 20 celebridades del mundo del streaming, redes sociales y creadores de contenido, que se subirán al ring en unas épicas peleas, junto a increíbles shows de música que se realizarán a lo largo de la noche.', 'https://www.instagram.com/parensedemanosok/?hl=es-la', 1), -- Ciudad de Buenos Aires
    ('Teatro Gran Rex', '2024-10-05', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/siames-live-in-buenos-aires.jpg', 'Siamés live in Buenos Aires', 'La música de Siames mezcla rock alternativo con tintes de pop y electrónica. Su sonido es característico, con guitarras potentes y letras que llegan al corazón. Tienen una onda que atrapa desde el primer acorde y no suelta hasta el último.', 'https://www.instagram.com/siamesmusic/?hl=es', 1), -- Ciudad de Buenos Aires
    ('Teatro Universidad', '2024-10-12', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/aventuras-perrunas.jpg', 'Aventuras Perrunas', 'Unos divertidos perritos muy coloridos junto a Luz, una alegre vecina, te invitan a pasar una tarde llena de risas y juegos, en donde el aprendizaje y el valor de la familia son lo más importante.', 'https://www.instagram.com/teatrouniversidad/?hl=es', 2), -- La Plata
    ('Multiespacio 880', '2024-12-14', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/san-telmo-tango-show.jpg', 'San Telmo Tango Show', 'Déjate cautivar por los ritmos y las historias que han dado vida al tango. Cada escena está cargada de emoción, pasión y tradición, transportándote al corazón de Buenos Aires.', 'https://www.instagram.com/multiespacio.880/', 3), -- San Telmo
    ('Teatro Stella Maris', '2024-09-15', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/las-aventuras-de-mario.jpg', 'Las aventuras de Mario', 'Nuestro Héroe en esta gran aventura por encontrar a su hermano conocerá a la Princesa y sus amigos, quiénes unirán sus fuerzas para rescatar a la estrella mágica y salvar al reino encantado.', 'https://www.instagram.com/teatrostellamaris/?hl=es', 4), -- Mar del Plata
    ('Doha Boliche', '2024-09-27', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/salvaje-fest.jpeg', 'Salvaje Fest', 'Un festival que sacará tu habilidad más SALVAJE. Conéctate con el entorno para enfrentar un recorrido exigente, con tramos de pavimento y destapado. Habrá segmentos cronometrados, puntos de hidratación, apoyo mecánico y al final comida, música y cerveza al estilo SALVAJE.', 'https://www.instagram.com/salvajegravelfest/', 5), -- Rosario
    ('Hipodromo de San Isidro', '2025-03-21', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/lollapalooza-2025.jpg', 'Lollapalooza 2025', 'Lo que sucede en #LollaAR no tiene comparación: 3 días con más de 100 artistas, en 5 escenarios llenos de variedad para que todos disfruten. Expresiones artísticas, actividades infantiles, oferta gastronómica para todos los gustos y una locación de ensueño.', 'https://www.instagram.com/lollapaloozaar/?hl=es', 6), -- San Isidro
    ('Teatro Astral', '2024-11-09', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/antigona-en-el-baño.jpg', 'Antigona en el baño', 'Ignacia (Verónica Llinás) es una estrella que está por salir a escena tras un largo exilio en TV. A la edad y la decadencia se suman miedos. Sus aliados: el hijo de su representante (Esteban Lamothe) y un "coach ontológico" (Héctor Díaz). ¿Será suficiente? El humor lo dirá.', 'https://www.instagram.com/antigonaobra/?hl=es', 1), -- Ciudad de Buenos Aires
    ('Predio de la Estación Belgrano', '2024-10-12', 'https://raw.githubusercontent.com/jimwux/Plataforma-de-eventos-Taller-Web/master/src/main/webapp/WEB-INF/assets/portadas-eventos/harlem-festival.jpg', 'Harlem Festival', 'El Harlem sigue creciendo en cada edición, reuniendo a miles de jóvenes, amigos y familias en Santa Fe con mega producción, escenarios y actividades. Desde su inicio en 2018, se posicionó como uno de los festivales más importantes de Argentina, con artistas como Wos, Duki y Nicki Nicole en el line-up.', 'https://www.instagram.com/harlemfestival/?hl=es', 7); -- Santa Fe
