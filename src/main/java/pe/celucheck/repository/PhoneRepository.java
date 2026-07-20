package pe.celucheck.repository;

import pe.celucheck.domain.Phone;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.*;

public interface PhoneRepository extends JpaRepository<Phone,Long> {
    @EntityGraph(attributePaths={"brand","offers","offers.store"})
    Optional<Phone> findBySlugAndActiveTrue(String slug);
    @EntityGraph(attributePaths={"brand","offers","offers.store"})
    @Query("""
      select distinct p from Phone p join p.offers o
      where p.active=true and o.inStock=true
        and (:q='' or lower(concat(p.name,' ',p.brand.name,' ',p.processor)) like lower(concat('%',:q,'%')))
        and (:brand='' or p.brand.slug=:brand)
        and (:maxPrice is null or o.price <= :maxPrice)
      order by p.featured desc, p.name asc
      """)
    List<Phone> search(@Param("q") String q, @Param("brand") String brand, @Param("maxPrice") BigDecimal maxPrice);
    @EntityGraph(attributePaths={"brand","offers","offers.store"})
    @Query("select p from Phone p where p.id=:id") Optional<Phone> findDetailedById(@Param("id") Long id);
    @EntityGraph(attributePaths={"brand","offers","offers.store"})
    @Query("select distinct p from Phone p order by p.id") List<Phone> findAllDetailed();
}
