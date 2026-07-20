package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="store_click")
@Getter @Setter @NoArgsConstructor
public class StoreClick {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private AppUser user;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="offer_id") private Offer offer;
    @Column(name="clicked_at", nullable=false, insertable=false, updatable=false) private LocalDateTime clickedAt;
}
