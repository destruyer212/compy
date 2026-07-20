CREATE TABLE phone_variant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone_id BIGINT NOT NULL,
    storage_gb INT NOT NULL,
    ram_gb INT NOT NULL,
    label VARCHAR(80) NOT NULL,
    default_variant BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_phone_variant_phone FOREIGN KEY (phone_id) REFERENCES phone(id) ON DELETE CASCADE,
    CONSTRAINT uq_phone_variant_capacity UNIQUE (phone_id, storage_gb, ram_gb),
    INDEX idx_phone_variant_phone (phone_id, active)
);

CREATE TABLE variant_offer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    variant_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    previous_price DECIMAL(10,2),
    product_url VARCHAR(700) NOT NULL,
    in_stock BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_variant_offer_variant FOREIGN KEY (variant_id) REFERENCES phone_variant(id) ON DELETE CASCADE,
    CONSTRAINT fk_variant_offer_store FOREIGN KEY (store_id) REFERENCES store(id),
    CONSTRAINT uq_variant_offer_store UNIQUE (variant_id, store_id),
    INDEX idx_variant_offer_best (variant_id, in_stock, price)
);
