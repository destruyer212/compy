package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "phone_variant")
@Getter @Setter @NoArgsConstructor
public class PhoneVariant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "phone_id")
    private Phone phone;

    @Column(name = "storage_gb", nullable = false)
    private Integer storageGb;

    @Column(name = "ram_gb", nullable = false)
    private Integer ramGb;

    @Column(nullable = false, length = 80)
    private String label;

    @Column(name = "default_variant", nullable = false)
    private boolean defaultVariant;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("inStock DESC, price ASC")
    private List<VariantOffer> offers = new ArrayList<>();
}
