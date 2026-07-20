INSERT INTO app_user (full_name, email, password, role) VALUES
('Administrador CeluCheck', 'admin@celucheck.pe', '{noop}Admin123!', 'ADMIN'),
('Mirko Coronel', 'mirko@correo.com', '{noop}Celu123!', 'USER'),
('Pedro Ponte', 'pedro@correo.com', '{noop}Celu123!', 'USER'),
('Marco Salas', 'marco@correo.com', '{noop}Celu123!', 'USER'),
('Luis Rodriguez', 'luis@correo.com', '{noop}Celu123!', 'USER');

UPDATE app_user SET enabled = FALSE WHERE email = 'marco@correo.com';

INSERT INTO brand (name, slug) VALUES
('Apple', 'apple'), ('Samsung', 'samsung'), ('Xiaomi', 'xiaomi'), ('Honor', 'honor'), ('Motorola', 'motorola');

INSERT INTO store (name, slug, logo_url, color_hex) VALUES
('Falabella', 'falabella', '/images/store-falabella.svg', '#c4d600'),
('Ripley', 'ripley', '/images/store-ripley.svg', '#111111'),
('Oechsle', 'oechsle', '/images/store-oechsle.svg', '#ef1b2d');

INSERT INTO phone (brand_id, name, slug, model, storage_gb, ram_gb, screen_inches, camera_mp, battery_mah, processor, image_url, description, featured) VALUES
((SELECT id FROM brand WHERE slug='apple'), 'iPhone 15 Pro Max 256GB', 'iphone-15-pro-max-256gb', 'A3108', 256, 8, 6.7, 48, 4441, 'Apple A17 Pro', '/images/phone-titanium.svg', 'Titanio, cámara profesional y un rendimiento extraordinario para usuarios exigentes.', TRUE),
((SELECT id FROM brand WHERE slug='samsung'), 'Samsung Galaxy S24 Ultra 256GB', 'samsung-galaxy-s24-ultra-256gb', 'SM-S928B', 256, 12, 6.8, 200, 5000, 'Snapdragon 8 Gen 3', '/images/phone-violet.svg', 'Galaxy AI, S Pen integrado y zoom de alto alcance en un diseño premium.', TRUE),
((SELECT id FROM brand WHERE slug='xiaomi'), 'Xiaomi Redmi Note 13 Pro 256GB', 'xiaomi-redmi-note-13-pro-256gb', '2312DRA50G', 256, 8, 6.7, 200, 5100, 'Snapdragon 7s Gen 2', '/images/phone-blue.svg', 'Gran cámara, batería duradera y pantalla AMOLED a un precio inteligente.', TRUE),
((SELECT id FROM brand WHERE slug='honor'), 'Honor Magic6 Pro 512GB', 'honor-magic6-pro-512gb', 'BVL-N49', 512, 12, 6.8, 180, 5600, 'Snapdragon 8 Gen 3', '/images/phone-green.svg', 'Fotografía teleobjetivo y autonomía sobresaliente con acabado elegante.', TRUE),
((SELECT id FROM brand WHERE slug='motorola'), 'Motorola Edge 50 Fusion 256GB', 'motorola-edge-50-fusion-256gb', 'XT2429-2', 256, 8, 6.7, 50, 5000, 'Snapdragon 7s Gen 2', '/images/phone-orange.svg', 'Pantalla curva fluida, carga rápida y experiencia Android limpia.', FALSE),
((SELECT id FROM brand WHERE slug='samsung'), 'Samsung Galaxy A55 5G 256GB', 'samsung-galaxy-a55-5g-256gb', 'SM-A556E', 256, 8, 6.6, 50, 5000, 'Exynos 1480', '/images/phone-navy.svg', 'Un gama media equilibrado con resistencia al agua y soporte prolongado.', FALSE);

INSERT INTO offer (phone_id, store_id, price, previous_price, product_url) VALUES
((SELECT id FROM phone WHERE slug='iphone-15-pro-max-256gb'), (SELECT id FROM store WHERE slug='ripley'), 4599, 5099, 'https://simple.ripley.com.pe/'),
((SELECT id FROM phone WHERE slug='iphone-15-pro-max-256gb'), (SELECT id FROM store WHERE slug='falabella'), 4799, 5199, 'https://www.falabella.com.pe/'),
((SELECT id FROM phone WHERE slug='iphone-15-pro-max-256gb'), (SELECT id FROM store WHERE slug='oechsle'), 4899, 5299, 'https://www.oechsle.pe/'),
((SELECT id FROM phone WHERE slug='samsung-galaxy-s24-ultra-256gb'), (SELECT id FROM store WHERE slug='ripley'), 5050, 5699, 'https://simple.ripley.com.pe/'),
((SELECT id FROM phone WHERE slug='samsung-galaxy-s24-ultra-256gb'), (SELECT id FROM store WHERE slug='falabella'), 5100, 5799, 'https://www.falabella.com.pe/'),
((SELECT id FROM phone WHERE slug='samsung-galaxy-s24-ultra-256gb'), (SELECT id FROM store WHERE slug='oechsle'), 5200, 5799, 'https://www.oechsle.pe/'),
((SELECT id FROM phone WHERE slug='xiaomi-redmi-note-13-pro-256gb'), (SELECT id FROM store WHERE slug='ripley'), 950, 1199, 'https://simple.ripley.com.pe/'),
((SELECT id FROM phone WHERE slug='xiaomi-redmi-note-13-pro-256gb'), (SELECT id FROM store WHERE slug='falabella'), 999, 1199, 'https://www.falabella.com.pe/'),
((SELECT id FROM phone WHERE slug='xiaomi-redmi-note-13-pro-256gb'), (SELECT id FROM store WHERE slug='oechsle'), 980, 1150, 'https://www.oechsle.pe/'),
((SELECT id FROM phone WHERE slug='honor-magic6-pro-512gb'), (SELECT id FROM store WHERE slug='falabella'), 3800, 4299, 'https://www.falabella.com.pe/'),
((SELECT id FROM phone WHERE slug='honor-magic6-pro-512gb'), (SELECT id FROM store WHERE slug='ripley'), 3850, 4299, 'https://simple.ripley.com.pe/'),
((SELECT id FROM phone WHERE slug='honor-magic6-pro-512gb'), (SELECT id FROM store WHERE slug='oechsle'), 3900, 4399, 'https://www.oechsle.pe/'),
((SELECT id FROM phone WHERE slug='motorola-edge-50-fusion-256gb'), (SELECT id FROM store WHERE slug='falabella'), 1199, 1499, 'https://www.falabella.com.pe/'),
((SELECT id FROM phone WHERE slug='motorola-edge-50-fusion-256gb'), (SELECT id FROM store WHERE slug='ripley'), 1249, 1499, 'https://simple.ripley.com.pe/'),
((SELECT id FROM phone WHERE slug='samsung-galaxy-a55-5g-256gb'), (SELECT id FROM store WHERE slug='oechsle'), 1399, 1699, 'https://www.oechsle.pe/'),
((SELECT id FROM phone WHERE slug='samsung-galaxy-a55-5g-256gb'), (SELECT id FROM store WHERE slug='falabella'), 1459, 1699, 'https://www.falabella.com.pe/');

INSERT INTO store_click (user_id, offer_id, clicked_at)
SELECT NULL, id, CURRENT_TIMESTAMP FROM offer WHERE id <= 8;
