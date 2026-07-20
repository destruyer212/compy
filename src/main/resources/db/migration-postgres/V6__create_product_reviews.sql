CREATE TABLE product_review (
    id BIGSERIAL PRIMARY KEY,
    phone_id BIGINT NOT NULL,
    author_name VARCHAR(80) NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment VARCHAR(700) NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_review_phone FOREIGN KEY (phone_id) REFERENCES phone(id) ON DELETE CASCADE
);
CREATE INDEX idx_product_review_phone_date ON product_review(phone_id, created_at);
