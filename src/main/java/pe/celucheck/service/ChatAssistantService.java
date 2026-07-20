package pe.celucheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.celucheck.domain.*;
import pe.celucheck.repository.*;
import java.util.Locale;

@Service @RequiredArgsConstructor
public class ChatAssistantService {
    private final ChatMessageRepository messages;
    private final AppUserRepository users;
    private final CatalogService catalog;

    @Transactional
    public String answer(String question, String email) {
        String q = question.toLowerCase(Locale.ROOT);
        String answer;
        if (q.contains("cámara") || q.contains("camara") || q.contains("foto")) {
            answer = "Para fotografía, el Galaxy S24 Ultra destaca por su sensor de 200 MP y zoom; el iPhone 15 Pro Max es excelente en video y consistencia. Si buscas valor, el Redmi Note 13 Pro ofrece 200 MP por menos de S/ 1,000.";
        } else if (q.contains("batería") || q.contains("bateria") || q.contains("dure")) {
            answer = "La mejor autonomía del catálogo es el Honor Magic6 Pro (5,600 mAh). El Redmi Note 13 Pro (5,100 mAh) es mi recomendación calidad-precio.";
        } else if (q.contains("barato") || q.contains("presupuesto") || q.matches(".*s/?\s?1[0-5]00.*")) {
            answer = "Con presupuesto ajustado elegiría el Redmi Note 13 Pro desde S/ 950. Tiene 8 GB de RAM, 256 GB y batería de 5,100 mAh; es el equilibrio más fuerte del catálogo.";
        } else if (q.contains("iphone") && (q.contains("samsung") || q.contains("android"))) {
            answer = "Elige iPhone 15 Pro Max si priorizas video, ecosistema Apple y soporte consistente. El Galaxy S24 Ultra conviene más si quieres zoom, personalización, S Pen y multitarea. En CeluCheck puedes comparar sus precios por tienda antes de decidir.";
        } else {
            var options = catalog.search("", "", null);
            answer = options.isEmpty() ? "Cuéntame tu presupuesto y qué valoras más: cámara, batería, juegos o tamaño." :
                    "Para recomendarte bien dime tu presupuesto y qué priorizas: cámara, batería, juegos o uso diario. Hoy la opción más económica visible es " + options.stream().min(java.util.Comparator.comparing(pe.celucheck.dto.ProductCard::bestPrice)).orElseThrow().name() + ".";
        }
        ChatMessage message = new ChatMessage(); message.setQuestion(question); message.setAnswer(answer);
        if (email != null) users.findByEmailIgnoreCase(email).ifPresent(message::setUser);
        messages.save(message); return answer;
    }
}
