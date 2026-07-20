package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "app_user")
@Getter @Setter @NoArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name="full_name", nullable=false, length=120) private String fullName;
    @Column(nullable=false, unique=true, length=180) private String email;
    @Column(nullable=false) private String password;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private Role role = Role.USER;
    @Column(nullable=false) private boolean enabled = true;
    @Column(name="created_at", nullable=false, insertable=false, updatable=false) private LocalDateTime createdAt;
    @Column(name="updated_at", nullable=false, insertable=false, updatable=false) private LocalDateTime updatedAt;
}
