INSERT INTO phone_variant (phone_id, storage_gb, ram_gb, label, default_variant, active)
SELECT p.id, specs.storage_gb, specs.ram_gb,
       CONCAT(CASE specs.storage_gb WHEN 1024 THEN '1 TB' WHEN 2048 THEN '2 TB' ELSE CONCAT(specs.storage_gb, ' GB') END,
              ' · ', specs.ram_gb, ' GB RAM'),
       CASE WHEN specs.storage_gb = p.storage_gb AND specs.ram_gb = p.ram_gb THEN TRUE ELSE FALSE END,
       TRUE
FROM phone p
JOIN (
    SELECT 'iphone-15-pro-max-256gb' slug, 256 storage_gb, 8 ram_gb UNION ALL
    SELECT 'iphone-15-pro-max-256gb', 512, 8 UNION ALL SELECT 'iphone-15-pro-max-256gb', 1024, 8 UNION ALL
    SELECT 'samsung-galaxy-s24-ultra-256gb', 256, 12 UNION ALL SELECT 'samsung-galaxy-s24-ultra-256gb', 512, 12 UNION ALL SELECT 'samsung-galaxy-s24-ultra-256gb', 1024, 12 UNION ALL
    SELECT 'xiaomi-redmi-note-13-pro-256gb', 256, 8 UNION ALL SELECT 'xiaomi-redmi-note-13-pro-256gb', 512, 12 UNION ALL
    SELECT 'honor-magic6-pro-512gb', 512, 12 UNION ALL
    SELECT 'motorola-edge-50-fusion-256gb', 256, 8 UNION ALL SELECT 'motorola-edge-50-fusion-256gb', 512, 12 UNION ALL
    SELECT 'samsung-galaxy-a55-5g-256gb', 128, 8 UNION ALL SELECT 'samsung-galaxy-a55-5g-256gb', 256, 8 UNION ALL
    SELECT 'iphone-16-pro-max-256gb', 256, 8 UNION ALL SELECT 'iphone-16-pro-max-256gb', 512, 8 UNION ALL SELECT 'iphone-16-pro-max-256gb', 1024, 8 UNION ALL
    SELECT 'iphone-16-128gb', 128, 8 UNION ALL SELECT 'iphone-16-128gb', 256, 8 UNION ALL SELECT 'iphone-16-128gb', 512, 8 UNION ALL
    SELECT 'iphone-17-pro-max-256gb', 256, 12 UNION ALL SELECT 'iphone-17-pro-max-256gb', 512, 12 UNION ALL SELECT 'iphone-17-pro-max-256gb', 1024, 12 UNION ALL SELECT 'iphone-17-pro-max-256gb', 2048, 12 UNION ALL
    SELECT 'samsung-galaxy-s25-ultra-256gb', 256, 12 UNION ALL SELECT 'samsung-galaxy-s25-ultra-256gb', 512, 12 UNION ALL SELECT 'samsung-galaxy-s25-ultra-256gb', 1024, 12 UNION ALL
    SELECT 'samsung-galaxy-s25-256gb', 128, 12 UNION ALL SELECT 'samsung-galaxy-s25-256gb', 256, 12 UNION ALL SELECT 'samsung-galaxy-s25-256gb', 512, 12 UNION ALL
    SELECT 'samsung-galaxy-z-fold7-512gb', 256, 12 UNION ALL SELECT 'samsung-galaxy-z-fold7-512gb', 512, 12 UNION ALL SELECT 'samsung-galaxy-z-fold7-512gb', 1024, 16 UNION ALL
    SELECT 'samsung-galaxy-z-flip7-256gb', 256, 12 UNION ALL SELECT 'samsung-galaxy-z-flip7-256gb', 512, 12 UNION ALL
    SELECT 'samsung-galaxy-s24-fe-256gb', 128, 8 UNION ALL SELECT 'samsung-galaxy-s24-fe-256gb', 256, 8 UNION ALL SELECT 'samsung-galaxy-s24-fe-256gb', 512, 8 UNION ALL
    SELECT 'xiaomi-14t-pro-512gb', 256, 12 UNION ALL SELECT 'xiaomi-14t-pro-512gb', 512, 12 UNION ALL SELECT 'xiaomi-14t-pro-512gb', 1024, 12 UNION ALL
    SELECT 'xiaomi-15-512gb', 256, 12 UNION ALL SELECT 'xiaomi-15-512gb', 512, 12 UNION ALL
    SELECT 'redmi-note-14-pro-plus-5g-512gb', 256, 8 UNION ALL SELECT 'redmi-note-14-pro-plus-5g-512gb', 512, 12 UNION ALL
    SELECT 'poco-f7-ultra-512gb', 256, 12 UNION ALL SELECT 'poco-f7-ultra-512gb', 512, 16 UNION ALL
    SELECT 'honor-magic7-pro-512gb', 512, 12 UNION ALL SELECT 'honor-magic7-pro-512gb', 1024, 16 UNION ALL
    SELECT 'honor-400-pro-512gb', 256, 12 UNION ALL SELECT 'honor-400-pro-512gb', 512, 12 UNION ALL
    SELECT 'motorola-edge-60-pro-512gb', 256, 12 UNION ALL SELECT 'motorola-edge-60-pro-512gb', 512, 12 UNION ALL
    SELECT 'motorola-razr-60-ultra-512gb', 512, 16 UNION ALL SELECT 'motorola-razr-60-ultra-512gb', 1024, 16 UNION ALL
    SELECT 'google-pixel-9-pro-xl-256gb', 128, 16 UNION ALL SELECT 'google-pixel-9-pro-xl-256gb', 256, 16 UNION ALL SELECT 'google-pixel-9-pro-xl-256gb', 512, 16 UNION ALL SELECT 'google-pixel-9-pro-xl-256gb', 1024, 16 UNION ALL
    SELECT 'oneplus-13-512gb', 256, 12 UNION ALL SELECT 'oneplus-13-512gb', 512, 16 UNION ALL
    SELECT 'oppo-find-x8-pro-512gb', 512, 16 UNION ALL SELECT 'oppo-find-x8-pro-512gb', 1024, 16 UNION ALL
    SELECT 'nothing-phone-3-256gb', 256, 12 UNION ALL SELECT 'nothing-phone-3-256gb', 512, 12
) specs ON specs.slug = p.slug;

INSERT INTO variant_offer (variant_id, store_id, price, previous_price, product_url, in_stock)
SELECT v.id, o.store_id,
       o.price + CASE
           WHEN v.storage_gb < p.storage_gb AND (p.storage_gb - v.storage_gb) >= 384 THEN -500
           WHEN v.storage_gb < p.storage_gb THEN -250
           WHEN v.storage_gb = p.storage_gb THEN 0
           WHEN v.storage_gb = 2048 THEN 2200
           WHEN (v.storage_gb - p.storage_gb) >= 768 THEN 1100
           WHEN (v.storage_gb - p.storage_gb) >= 256 THEN 500
           ELSE 250
       END,
       CASE WHEN o.previous_price IS NULL THEN NULL ELSE o.previous_price + CASE
           WHEN v.storage_gb < p.storage_gb AND (p.storage_gb - v.storage_gb) >= 384 THEN -500
           WHEN v.storage_gb < p.storage_gb THEN -250
           WHEN v.storage_gb = p.storage_gb THEN 0
           WHEN v.storage_gb = 2048 THEN 2200
           WHEN (v.storage_gb - p.storage_gb) >= 768 THEN 1100
           WHEN (v.storage_gb - p.storage_gb) >= 256 THEN 500
           ELSE 250
       END END,
       o.product_url,
       CASE WHEN v.storage_gb >= 1024 AND MOD(o.store_id + p.id, 3) = 0 THEN FALSE ELSE o.in_stock END
FROM phone_variant v
JOIN phone p ON p.id = v.phone_id
JOIN offer o ON o.phone_id = p.id;
