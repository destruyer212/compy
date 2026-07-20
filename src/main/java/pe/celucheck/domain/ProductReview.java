package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_review")
@Getter @Setter @NoArgsConstructor
public class ProductReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "phone_id")
    private Phone phone;

    @Column(name = "author_name", nullable = false, length = 80)
    private String authorName;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 700)
    private String comment;

    @Column(nullable = false)
    private boolean verified;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
