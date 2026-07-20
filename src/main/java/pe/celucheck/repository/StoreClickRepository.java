package pe.celucheck.repository;
import pe.celucheck.domain.StoreClick;
import org.springframework.data.jpa.repository.*;
import java.util.List;
public interface StoreClickRepository extends JpaRepository<StoreClick,Long> {
    @Query("select sc.offer.store.name, count(sc) from StoreClick sc group by sc.offer.store.name order by count(sc) desc")
    List<Object[]> clicksByStore();
}
