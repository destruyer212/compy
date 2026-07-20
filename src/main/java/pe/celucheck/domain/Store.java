package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="store")
@Getter @Setter @NoArgsConstructor
public class Store {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=100) private String name;
    @Column(nullable=false, unique=true, length=110) private String slug;
    @Column(name="logo_url", length=500) private String logoUrl;
    @Column(name="color_hex", nullable=false, length=7) private String colorHex;
    @Column(nullable=false) private boolean active = true;
}
