INSERT INTO brand (name, slug) VALUES
('Google', 'google'), ('OnePlus', 'oneplus'), ('OPPO', 'oppo'), ('Nothing', 'nothing'),
('POCO', 'poco'), ('vivo', 'vivo');

INSERT INTO phone (brand_id, name, slug, model, storage_gb, ram_gb, screen_inches, camera_mp, battery_mah, processor, image_url, description, featured) VALUES
((SELECT id FROM brand WHERE slug='apple'), 'iPhone 16 Pro Max 256GB', 'iphone-16-pro-max-256gb', 'A3296', 256, 8, 6.9, 48, 4685, 'Apple A18 Pro', 'https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-16-pro-max.jpg', 'Un flagship de titanio pensado para video, fotografía computacional y rendimiento sostenido, con una pantalla amplia y una experiencia premium.', TRUE),
((SELECT id FROM brand WHERE slug='apple'), 'iPhone 16 128GB', 'iphone-16-128gb', 'A3287', 128, 8, 6.1, 48, 3561, 'Apple A18', 'https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-16.jpg', 'Potencia de última generación en un formato cómodo, con excelente video, ecosistema sólido y años de actualizaciones.', FALSE),
((SELECT id FROM brand WHERE slug='apple'), 'iPhone 17 Pro Max 256GB', 'iphone-17-pro-max-256gb', 'A3526', 256, 12, 6.9, 48, 5088, 'Apple A19 Pro', 'https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-17-pro-max.jpg', 'La propuesta más avanzada de Apple para creadores y usuarios intensivos que buscan máxima autonomía, rendimiento y calidad de video.', TRUE),
((SELECT id FROM brand WHERE slug='samsung'), 'Samsung Galaxy S25 Ultra 256GB', 'samsung-galaxy-s25-ultra-256gb', 'SM-S938B', 256, 12, 6.9, 200, 5000, 'Snapdragon 8 Elite', 'https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-s25-ultra-sm-s938.jpg', 'Cámara de 200 MP, S Pen y potencia de élite para productividad, juegos y fotografía de largo alcance.', TRUE),
((SELECT id FROM brand WHERE slug='samsung'), 'Samsung Galaxy S25 256GB', 'samsung-galaxy-s25-256gb', 'SM-S931B', 256, 12, 6.2, 50, 4000, 'Snapdragon 8 Elite', 'https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-s25-sm-s931.jpg', 'Un gama alta compacto, rápido y refinado con cámaras versátiles y funciones inteligentes para el día a día.', FALSE),
((SELECT id FROM brand WHERE slug='samsung'), 'Samsung Galaxy Z Fold7 512GB', 'samsung-galaxy-z-fold7-512gb', 'SM-F966B', 512, 12, 8.0, 200, 4400, 'Snapdragon 8 Elite', 'https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-z-fold7.jpg', 'Productividad de tablet en un plegable ultradelgado, con enorme pantalla interior y cámara principal de alta resolución.', TRUE),
((SELECT id FROM brand WHERE slug='samsung'), 'Samsung Galaxy Z Flip7 256GB', 'samsung-galaxy-z-flip7-256gb', 'SM-F766B', 256, 12, 6.9, 50, 4300, 'Exynos 2500', 'https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-z-flip7.jpg', 'Diseño plegable compacto con gran pantalla exterior, ideal para estilo, contenido social y portabilidad.', TRUE),
((SELECT id FROM brand WHERE slug='samsung'), 'Samsung Galaxy S24 FE 256GB', 'samsung-galaxy-s24-fe-256gb', 'SM-S721B', 256, 8, 6.7, 50, 4700, 'Exynos 2400e', 'https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-s24-fe.jpg', 'Experiencia Galaxy premium a un precio más accesible, con pantalla fluida, cámaras equilibradas y soporte prolongado.', FALSE),
((SELECT id FROM brand WHERE slug='xiaomi'), 'Xiaomi 14T Pro 512GB', 'xiaomi-14t-pro-512gb', '2407FPN8EG', 512, 12, 6.7, 50, 5000, 'Dimensity 9300+', 'https://fdn2.gsmarena.com/vv/bigpic/xiaomi-14t-pro.jpg', 'Alto rendimiento, óptica versátil y carga ultrarrápida para quienes quieren especificaciones flagship a precio competitivo.', TRUE),
((SELECT id FROM brand WHERE slug='xiaomi'), 'Xiaomi 15 512GB', 'xiaomi-15-512gb', '24129PN74G', 512, 12, 6.4, 50, 5240, 'Snapdragon 8 Elite', 'https://fdn2.gsmarena.com/vv/bigpic/xiaomi-15.jpg', 'Potencia flagship en un cuerpo compacto, con gran autonomía y un sistema de cámaras pensado para fotografía móvil.', TRUE),
((SELECT id FROM brand WHERE slug='xiaomi'), 'Redmi Note 14 Pro+ 5G 512GB', 'redmi-note-14-pro-plus-5g-512gb', '24115RA8EG', 512, 12, 6.7, 200, 5110, 'Snapdragon 7s Gen 3', 'https://fdn2.gsmarena.com/vv/bigpic/xiaomi-redmi-note-14-pro-plus-5g.jpg', 'Gama media premium con cámara de 200 MP, gran almacenamiento y carga veloz para una excelente relación calidad-precio.', FALSE),
((SELECT id FROM brand WHERE slug='poco'), 'POCO F7 Ultra 512GB', 'poco-f7-ultra-512gb', '24122RKC7G', 512, 16, 6.7, 50, 5300, 'Snapdragon 8 Elite', 'https://fdn2.gsmarena.com/vv/bigpic/xiaomi-poco-f7-ultra.jpg', 'Rendimiento extremo para gaming, pantalla de alta respuesta y enorme memoria sin pagar el precio de un flagship tradicional.', TRUE),
((SELECT id FROM brand WHERE slug='honor'), 'Honor Magic7 Pro 512GB', 'honor-magic7-pro-512gb', 'PTP-N49', 512, 12, 6.8, 200, 5270, 'Snapdragon 8 Elite', 'https://fdn2.gsmarena.com/vv/bigpic/honor-magic7-pro.jpg', 'Fotografía teleobjetivo, batería de larga duración y diseño premium para usuarios que buscan una alternativa completa.', TRUE),
((SELECT id FROM brand WHERE slug='honor'), 'Honor 400 Pro 512GB', 'honor-400-pro-512gb', 'DNP-NX9', 512, 12, 6.7, 200, 5300, 'Snapdragon 8 Gen 3', 'https://fdn2.gsmarena.com/vv/bigpic/honor-400-pro.jpg', 'Cámara de alta resolución, mucha memoria y batería generosa para creación de contenido y uso intensivo.', FALSE),
((SELECT id FROM brand WHERE slug='motorola'), 'Motorola Edge 60 Pro 512GB', 'motorola-edge-60-pro-512gb', 'XT2507', 512, 12, 6.7, 50, 6000, 'Dimensity 8350 Extreme', 'https://fdn2.gsmarena.com/vv/bigpic/motorola-edge-60-pro.jpg', 'Pantalla envolvente, batería sobresaliente y Android limpio en un equipo equilibrado para trabajo y entretenimiento.', FALSE),
((SELECT id FROM brand WHERE slug='motorola'), 'Motorola Razr 60 Ultra 512GB', 'motorola-razr-60-ultra-512gb', 'XT2551', 512, 16, 7.0, 50, 4700, 'Snapdragon 8 Elite', 'https://fdn2.gsmarena.com/vv/bigpic/motorola-razr-60-ultra.jpg', 'Plegable sofisticado con pantalla exterior útil, memoria abundante y rendimiento de primera línea.', TRUE),
((SELECT id FROM brand WHERE slug='google'), 'Google Pixel 9 Pro XL 256GB', 'google-pixel-9-pro-xl-256gb', 'GGX8B', 256, 16, 6.8, 50, 5060, 'Google Tensor G4', 'https://fdn2.gsmarena.com/vv/bigpic/google-pixel-9-pro-xl-.jpg', 'Fotografía computacional de referencia, Android puro y funciones inteligentes exclusivas en formato grande.', TRUE),
((SELECT id FROM brand WHERE slug='oneplus'), 'OnePlus 13 512GB', 'oneplus-13-512gb', 'CPH2653', 512, 16, 6.8, 50, 6000, 'Snapdragon 8 Elite', 'https://fdn2.gsmarena.com/vv/bigpic/oneplus-13.jpg', 'Velocidad, batería enorme y pantalla premium para usuarios que priorizan fluidez y rendimiento sin concesiones.', TRUE),
((SELECT id FROM brand WHERE slug='oppo'), 'OPPO Find X8 Pro 512GB', 'oppo-find-x8-pro-512gb', 'CPH2659', 512, 16, 6.8, 50, 5910, 'Dimensity 9400', 'https://fdn2.gsmarena.com/vv/bigpic/oppo-find-x8-pro.jpg', 'Sistema de cámaras versátil, acabado refinado y excelente autonomía para fotografía, viajes y productividad.', TRUE),
((SELECT id FROM brand WHERE slug='nothing'), 'Nothing Phone (3) 256GB', 'nothing-phone-3-256gb', 'A024', 256, 12, 6.7, 50, 5150, 'Snapdragon 8s Gen 4', 'https://fdn2.gsmarena.com/vv/bigpic/nothing-phone-3.jpg', 'Diseño audaz e interfaz distintiva para quien busca un celular diferente, rápido y muy bien construido.', TRUE);

INSERT INTO offer (phone_id, store_id, price, previous_price, product_url)
SELECT p.id, s.id,
  base.base_price + CASE s.slug WHEN 'ripley' THEN 0 WHEN 'falabella' THEN 80 ELSE 150 END,
  base.base_price + CASE s.slug WHEN 'ripley' THEN 500 WHEN 'falabella' THEN 580 ELSE 650 END,
  CASE s.slug WHEN 'ripley' THEN 'https://simple.ripley.com.pe/' WHEN 'falabella' THEN 'https://www.falabella.com.pe/' ELSE 'https://www.oechsle.pe/' END
FROM phone p
JOIN (
  SELECT 'iphone-16-pro-max-256gb' slug, 4899 base_price UNION ALL SELECT 'iphone-16-128gb', 3099
  UNION ALL SELECT 'iphone-17-pro-max-256gb', 5699 UNION ALL SELECT 'samsung-galaxy-s25-ultra-256gb', 4699
  UNION ALL SELECT 'samsung-galaxy-s25-256gb', 3199 UNION ALL SELECT 'samsung-galaxy-z-fold7-512gb', 6999
  UNION ALL SELECT 'samsung-galaxy-z-flip7-256gb', 3799 UNION ALL SELECT 'samsung-galaxy-s24-fe-256gb', 2199
  UNION ALL SELECT 'xiaomi-14t-pro-512gb', 2599 UNION ALL SELECT 'xiaomi-15-512gb', 3199
  UNION ALL SELECT 'redmi-note-14-pro-plus-5g-512gb', 1899 UNION ALL SELECT 'poco-f7-ultra-512gb', 2499
  UNION ALL SELECT 'honor-magic7-pro-512gb', 3999 UNION ALL SELECT 'honor-400-pro-512gb', 2699
  UNION ALL SELECT 'motorola-edge-60-pro-512gb', 2299 UNION ALL SELECT 'motorola-razr-60-ultra-512gb', 4599
  UNION ALL SELECT 'google-pixel-9-pro-xl-256gb', 3999 UNION ALL SELECT 'oneplus-13-512gb', 3499
  UNION ALL SELECT 'oppo-find-x8-pro-512gb', 3899 UNION ALL SELECT 'nothing-phone-3-256gb', 2899
) base ON base.slug = p.slug
CROSS JOIN store s;
