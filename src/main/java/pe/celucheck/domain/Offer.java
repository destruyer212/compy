package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="offer")
@Getter @Setter @NoArgsConstructor
public class Offer {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="phone_id") private Phone phone;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="store_id") private Store store;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal price;
    @Column(name="previous_price", precision=10, scale=2) private BigDecimal previousPrice;
    @Column(name="product_url", nullable=false, length=700) private String productUrl;
    @Column(name="in_stock", nullable=false) private boolean inStock = true;
    @Column(name="last_checked_at", nullable=false, insertable=false, updatable=false) private LocalDateTime lastCheckedAt;
}
