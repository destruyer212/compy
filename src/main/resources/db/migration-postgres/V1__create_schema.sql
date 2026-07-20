CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(120) NOT NULL,
    email VARCHAR(180) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE brand (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(80) NOT NULL UNIQUE,
    slug VARCHAR(90) NOT NULL UNIQUE
);

CREATE TABLE store (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    slug VARCHAR(110) NOT NULL UNIQUE,
    logo_url VARCHAR(500),
    color_hex VARCHAR(7) NOT NULL DEFAULT '#0f172a',
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE phone (
    id BIGSERIAL PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    name VARCHAR(180) NOT NULL,
    slug VARCHAR(200) NOT NULL UNIQUE,
    model VARCHAR(120) NOT NULL,
    storage_gb INT NOT NULL,
    ram_gb INT NOT NULL,
    screen_inches DECIMAL(3,1) NOT NULL,
    camera_mp INT NOT NULL,
    battery_mah INT NOT NULL,
    processor VARCHAR(140) NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    description TEXT NOT NULL,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_phone_brand FOREIGN KEY (brand_id) REFERENCES brand(id)
);
CREATE INDEX idx_phone_name ON phone(name);
CREATE INDEX idx_phone_specs ON phone(storage_gb, ram_gb, battery_mah);

CREATE TABLE offer (
    id BIGSERIAL PRIMARY KEY,
    phone_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    previous_price DECIMAL(10,2),
    product_url VARCHAR(700) NOT NULL,
    in_stock BOOLEAN NOT NULL DEFAULT TRUE,
    last_checked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_offer_phone FOREIGN KEY (phone_id) REFERENCES phone(id) ON DELETE CASCADE,
    CONSTRAINT fk_offer_store FOREIGN KEY (store_id) REFERENCES store(id),
    CONSTRAINT uq_offer_phone_store UNIQUE (phone_id, store_id)
);
CREATE INDEX idx_offer_best_price ON offer(phone_id, in_stock, price);

CREATE TABLE favorite (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    phone_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_favorite_user FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_phone FOREIGN KEY (phone_id) REFERENCES phone(id) ON DELETE CASCADE,
    CONSTRAINT uq_favorite UNIQUE (user_id, phone_id)
);

CREATE TABLE store_click (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    offer_id BIGINT NOT NULL,
    clicked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_click_user FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE SET NULL,
    CONSTRAINT fk_click_offer FOREIGN KEY (offer_id) REFERENCES offer(id) ON DELETE CASCADE
);
CREATE INDEX idx_click_date ON store_click(clicked_at);

CREATE TABLE chat_message (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    question VARCHAR(1000) NOT NULL,
    answer TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_chat_user FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE SET NULL
);
CREATE INDEX idx_chat_date ON chat_message(created_at);
