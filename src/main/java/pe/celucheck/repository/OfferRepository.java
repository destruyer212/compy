package pe.celucheck.repository;
import pe.celucheck.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OfferRepository extends JpaRepository<Offer,Long> {
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths={"store","phone"})
    java.util.Optional<Offer> findDetailedById(Long id);
}
