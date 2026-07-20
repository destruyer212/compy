package pe.celucheck.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.celucheck.domain.PhoneVariant;

import java.util.List;

public interface PhoneVariantRepository extends JpaRepository<PhoneVariant, Long> {
    @EntityGraph(attributePaths = {"offers", "offers.store"})
    List<PhoneVariant> findByPhoneIdAndActiveTrueOrderByStorageGbAsc(Long phoneId);
}
