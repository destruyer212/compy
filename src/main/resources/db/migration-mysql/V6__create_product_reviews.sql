CREATE TABLE product_review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone_id BIGINT NOT NULL,
    author_name VARCHAR(80) NOT NULL,
    rating INT NOT NULL,
    comment VARCHAR(700) NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_review_phone FOREIGN KEY (phone_id) REFERENCES phone(id) ON DELETE CASCADE,
    CONSTRAINT chk_product_review_rating CHECK (rating BETWEEN 1 AND 5),
    INDEX idx_product_review_phone_date (phone_id, created_at)
);
