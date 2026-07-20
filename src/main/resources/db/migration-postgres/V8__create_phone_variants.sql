CREATE TABLE phone_variant (
    id BIGSERIAL PRIMARY KEY,
    phone_id BIGINT NOT NULL,
    storage_gb INT NOT NULL,
    ram_gb INT NOT NULL,
    label VARCHAR(80) NOT NULL,
    default_variant BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_phone_variant_phone FOREIGN KEY (phone_id) REFERENCES phone(id) ON DELETE CASCADE,
    CONSTRAINT uq_phone_variant_capacity UNIQUE (phone_id, storage_gb, ram_gb)
);
CREATE INDEX idx_phone_variant_phone ON phone_variant(phone_id, active);

CREATE TABLE variant_offer (
    id BIGSERIAL PRIMARY KEY,
    variant_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    previous_price DECIMAL(10,2),
    product_url VARCHAR(700) NOT NULL,
    in_stock BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_variant_offer_variant FOREIGN KEY (variant_id) REFERENCES phone_variant(id) ON DELETE CASCADE,
    CONSTRAINT fk_variant_offer_store FOREIGN KEY (store_id) REFERENCES store(id),
    CONSTRAINT uq_variant_offer_store UNIQUE (variant_id, store_id)
);
CREATE INDEX idx_variant_offer_best ON variant_offer(variant_id, in_stock, price);
