package pe.celucheck.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.celucheck.repository.FavoriteRepository;
import pe.celucheck.service.UserService;

@ControllerAdvice @RequiredArgsConstructor
public class GlobalModelAdvice {
    private final UserService users; private final FavoriteRepository favorites;
    @ModelAttribute("currentUserName")
    public String currentUserName(Authentication auth){ return auth == null ? null : users.byEmail(auth.getName()).getFullName(); }
    @ModelAttribute("favoriteCount")
    public long favoriteCount(Authentication auth){ return auth == null ? 0 : favorites.countByUser(users.byEmail(auth.getName())); }
    @ModelAttribute("currentUserAdmin")
    public boolean currentUserAdmin(Authentication auth){ return auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")); }
}
