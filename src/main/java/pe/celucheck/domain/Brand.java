package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="brand")
@Getter @Setter @NoArgsConstructor
public class Brand {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=80) private String name;
    @Column(nullable=false, unique=true, length=90) private String slug;
}
