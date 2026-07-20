package pe.celucheck.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pe.celucheck.domain.StoreClick;
import pe.celucheck.repository.*;
import pe.celucheck.service.UserService;

@Controller @RequiredArgsConstructor
public class ClickController {
    private final OfferRepository offers; private final StoreClickRepository clicks; private final UserService users;
    @GetMapping("/click/{offerId}")
    String click(@PathVariable Long offerId, Authentication auth){
        var offer=offers.findDetailedById(offerId).orElseThrow(); StoreClick click=new StoreClick(); click.setOffer(offer);
        if(auth!=null) click.setUser(users.byEmail(auth.getName())); clicks.save(click); return "redirect:"+offer.getProductUrl();
    }
}
