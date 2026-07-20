INSERT INTO product_review (phone_id, author_name, rating, comment, verified)
SELECT ranked.id,
       CASE MOD(((ranked.phone_position - 1) * 4 + seed.review_no), 20)
           WHEN 0 THEN 'Andrea M.' WHEN 1 THEN 'Diego R.' WHEN 2 THEN 'Lucía P.' WHEN 3 THEN 'Marco A.'
           WHEN 4 THEN 'Valeria C.' WHEN 5 THEN 'José L.' WHEN 6 THEN 'Camila V.' WHEN 7 THEN 'Renato G.'
           WHEN 8 THEN 'Paola S.' WHEN 9 THEN 'Bruno T.' WHEN 10 THEN 'Fernanda Q.' WHEN 11 THEN 'Carlos N.'
           WHEN 12 THEN 'María E.' WHEN 13 THEN 'Sebastián D.' WHEN 14 THEN 'Alejandra F.' WHEN 15 THEN 'Luis B.'
           WHEN 16 THEN 'Natalia H.' WHEN 17 THEN 'Jorge P.' WHEN 18 THEN 'Daniela K.' ELSE 'Miguel C.'
       END,
       CASE seed.review_no WHEN 1 THEN 5 WHEN 2 THEN 5 WHEN 3 THEN 4 ELSE 5 END,
       CASE seed.review_no
           WHEN 1 THEN CONCAT('La pantalla de ', ranked.name, ' se siente muy fluida y el procesador ', ranked.processor, ' responde rápido incluso al cambiar entre varias apps.')
           WHEN 2 THEN CONCAT('Probé la cámara de ', ranked.camera_mp, ' MP del ', ranked.name, ' y me gustó el nivel de detalle; la batería de ', ranked.battery_mah, ' mAh llegó bien al final del día.')
           WHEN 3 THEN CONCAT('Sus ', ranked.storage_gb, ' GB de espacio y ', ranked.ram_gb, ' GB de RAM hacen que el ', ranked.name, ' sea una opción muy completa; igual conviene comparar el precio entre tiendas.')
           ELSE CONCAT('Después de comparar varios modelos elegí el ', ranked.name, '. La combinación de pantalla de ', ranked.screen_inches, ' pulgadas, buena autonomía y memoria encaja con mi uso diario.')
       END,
       TRUE
FROM (
    SELECT p.id, p.name, p.storage_gb, p.ram_gb, p.screen_inches, p.camera_mp, p.battery_mah, p.processor,
           ROW_NUMBER() OVER (ORDER BY p.id) AS phone_position
    FROM phone p
) ranked
CROSS JOIN (
    SELECT 1 AS review_no UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
) seed
WHERE ranked.phone_position <= 22 OR seed.review_no <= 3;
