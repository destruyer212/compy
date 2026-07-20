package pe.celucheck.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.celucheck.domain.ProductReview;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByPhoneIdOrderByCreatedAtDescIdDesc(Long phoneId);
}
