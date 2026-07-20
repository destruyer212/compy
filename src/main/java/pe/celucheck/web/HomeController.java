package pe.celucheck.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.celucheck.dto.RegisterRequest;
import pe.celucheck.repository.BrandRepository;
import pe.celucheck.service.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Controller @RequiredArgsConstructor
public class HomeController {
    private final CatalogService catalog; private final BrandRepository brands; private final UserService users; private final FavoriteService favorites;

    @GetMapping("/")
    String home(@RequestParam(defaultValue="") String q, @RequestParam(defaultValue="") String brand,
                @RequestParam(required=false) BigDecimal maxPrice,
                @RequestParam(defaultValue="") String profile, Model model) {
        var products = catalog.search(q,brand,maxPrice,profile);
        model.addAttribute("products", products); model.addAttribute("brands",brands.findAll());
        model.addAttribute("q",q); model.addAttribute("selectedBrand",brand); model.addAttribute("maxPrice",maxPrice);
        model.addAttribute("selectedProfile", profile);
        model.addAttribute("newProducts", catalog.search("","",null).stream().limit(8).toList());
        return "home";
    }
    @GetMapping("/catalogo")
    String catalog(@RequestParam(defaultValue="") String q, @RequestParam(defaultValue="") String brand,
                   @RequestParam(required=false) BigDecimal maxPrice,
                   @RequestParam(defaultValue="") String profile, Model model) {
        model.addAttribute("products", catalog.search(q,brand,maxPrice,profile));
        model.addAttribute("brands",brands.findAll()); model.addAttribute("q",q);
        model.addAttribute("selectedBrand",brand); model.addAttribute("maxPrice",maxPrice);
        model.addAttribute("selectedProfile",profile); return "catalog";
    }
    @GetMapping("/productos/{slug}") String detail(@PathVariable String slug, Model model){ model.addAttribute("phone",catalog.detail(slug)); return "detail"; }
    @GetMapping("/comparar")
    String compare(@RequestParam(defaultValue="") String ids, Model model) {
        List<Long> phoneIds = Arrays.stream(ids.split(",")).map(String::trim).filter(s -> s.matches("\\d+"))
                .map(Long::valueOf).distinct().limit(3).toList();
        model.addAttribute("phones", catalog.compare(phoneIds));
        return "compare";
    }
    @GetMapping("/nosotros") String about(){ return "about"; }
    @GetMapping("/login") String login(){ return "login"; }
    @GetMapping("/registro") String registerForm(Model model){ if(!model.containsAttribute("registerRequest")) model.addAttribute("registerRequest",new RegisterRequest()); return "register"; }
    @PostMapping("/registro")
    String register(@Valid @ModelAttribute RegisterRequest registerRequest, BindingResult binding, RedirectAttributes redirect){
        if(binding.hasErrors()) return "register";
        try { users.register(registerRequest); redirect.addFlashAttribute("success","Cuenta creada. Ya puedes iniciar sesión."); return "redirect:/login"; }
        catch(IllegalArgumentException ex){ binding.reject("register",ex.getMessage()); return "register"; }
    }
    @GetMapping("/favoritos") String favorites(Authentication auth, Model model){ model.addAttribute("favorites",favorites.list(auth.getName())); return "favorites"; }
}
