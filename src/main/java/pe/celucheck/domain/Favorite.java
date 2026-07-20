package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="favorite", uniqueConstraints=@UniqueConstraint(columnNames={"user_id","phone_id"}))
@Getter @Setter @NoArgsConstructor
public class Favorite {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="user_id") private AppUser user;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="phone_id") private Phone phone;
    @Column(name="created_at", nullable=false, insertable=false, updatable=false) private LocalDateTime createdAt;
}
