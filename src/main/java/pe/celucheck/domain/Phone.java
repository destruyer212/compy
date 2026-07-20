package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="phone")
@Getter @Setter @NoArgsConstructor
public class Phone {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="brand_id") private Brand brand;
    @Column(nullable=false, length=180) private String name;
    @Column(nullable=false, unique=true, length=200) private String slug;
    @Column(nullable=false, length=120) private String model;
    @Column(name="storage_gb", nullable=false) private Integer storageGb;
    @Column(name="ram_gb", nullable=false) private Integer ramGb;
    @Column(name="screen_inches", nullable=false, precision=3, scale=1) private BigDecimal screenInches;
    @Column(name="camera_mp", nullable=false) private Integer cameraMp;
    @Column(name="battery_mah", nullable=false) private Integer batteryMah;
    @Column(nullable=false, length=140) private String processor;
    @Column(name="image_url", nullable=false, length=500) private String imageUrl;
    @Column(nullable=false, columnDefinition="TEXT") private String description;
    @Column(nullable=false) private boolean featured;
    @Column(nullable=false) private boolean active = true;
    @Column(name="created_at", nullable=false, insertable=false, updatable=false) private LocalDateTime createdAt;
    @Column(name="updated_at", nullable=false, insertable=false, updatable=false) private LocalDateTime updatedAt;
    @OneToMany(mappedBy="phone", cascade=CascadeType.ALL, orphanRemoval=true)
    @OrderBy("price ASC") private List<Offer> offers = new ArrayList<>();
}
