package pe.celucheck.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.celucheck.domain.*;
import pe.celucheck.repository.FavoriteRepository;
import java.util.List;

@Service @RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favorites;
    private final UserService users;
    private final CatalogService catalog;
    @Transactional
    public boolean toggle(String email, Long phoneId) {
        AppUser user = users.byEmail(email); Phone phone = catalog.byId(phoneId);
        var current = favorites.findByUserAndPhone(user, phone);
        if (current.isPresent()) { favorites.delete(current.get()); return false; }
        Favorite favorite = new Favorite(); favorite.setUser(user); favorite.setPhone(phone); favorites.save(favorite); return true;
    }
    @Transactional(readOnly=true)
    public List<Favorite> list(String email) { return favorites.findByUserOrderByCreatedAtDesc(users.byEmail(email)); }
}
