package pe.celucheck.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="chat_message")
@Getter @Setter @NoArgsConstructor
public class ChatMessage {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id") private AppUser user;
    @Column(nullable=false, length=1000) private String question;
    @Column(nullable=false, columnDefinition="TEXT") private String answer;
    @Column(name="created_at", nullable=false, insertable=false, updatable=false) private LocalDateTime createdAt;
}
