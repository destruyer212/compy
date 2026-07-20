package pe.celucheck.repository;
import pe.celucheck.domain.*;
import org.springframework.data.jpa.repository.*;
import java.util.*;
public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
    Optional<Favorite> findByUserAndPhone(AppUser user, Phone phone);
    @EntityGraph(attributePaths={"phone","phone.brand","phone.offers","phone.offers.store"})
    List<Favorite> findByUserOrderByCreatedAtDesc(AppUser user);
    long countByUser(AppUser user);
}
