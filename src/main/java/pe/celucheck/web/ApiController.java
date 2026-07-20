package pe.celucheck.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.celucheck.dto.*;
import pe.celucheck.service.*;
import java.math.BigDecimal;
import java.util.*;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
public class ApiController {
    private final CatalogService catalog; private final FavoriteService favorites; private final ChatAssistantService chat;
    @GetMapping("/products") List<ProductCard> products(@RequestParam(defaultValue="") String q,@RequestParam(defaultValue="") String brand,@RequestParam(required=false) BigDecimal maxPrice,@RequestParam(defaultValue="") String profile){ return catalog.search(q,brand,maxPrice,profile); }
    @PostMapping("/favorites/{phoneId}") Map<String,Object> toggle(@PathVariable Long phoneId, Authentication auth){ boolean saved=favorites.toggle(auth.getName(),phoneId); return Map.of("saved",saved); }
    @PostMapping("/chat") ChatResponse chat(@Valid @RequestBody ChatRequest request, Authentication auth){ return new ChatResponse(chat.answer(request.message(),auth==null?null:auth.getName())); }
}
