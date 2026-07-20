package pe.celucheck.repository;
import pe.celucheck.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {}
