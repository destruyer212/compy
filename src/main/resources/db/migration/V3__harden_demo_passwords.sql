-- Reemplaza las claves temporales de la migración inicial por BCrypt con factor 12.
UPDATE app_user
SET password = '$2a$12$q4bqa2cd3TpjYDycP4HJfOhWsBmdkOATG5GPCttjOpHW56u4YIEmu'
WHERE email = 'admin@celucheck.pe';

UPDATE app_user
SET password = '$2a$12$nv0UEqYlaPkoiIuph0ZSAOkZqTin0bMxovdwq8kcUo8ZSrm57p2v.'
WHERE email IN ('mirko@correo.com', 'pedro@correo.com', 'marco@correo.com', 'luis@correo.com');
