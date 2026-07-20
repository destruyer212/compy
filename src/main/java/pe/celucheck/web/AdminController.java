package pe.celucheck.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.celucheck.dto.AdminPhoneForm;
import pe.celucheck.repository.*;
import pe.celucheck.service.AdminService;

@Controller @RequestMapping("/admin") @RequiredArgsConstructor
public class AdminController {
    private final PhoneRepository phones; private final BrandRepository brands; private final AppUserRepository users;
    private final StoreClickRepository clicks; private final ChatMessageRepository chats; private final AdminService admin;
    @GetMapping String dashboard(Model model){
        var byStore=clicks.clicksByStore(); long total=clicks.count();
        model.addAttribute("clicks",total); model.addAttribute("commission",total*2.75); model.addAttribute("chatCount",chats.count());
        model.addAttribute("userCount",users.count()); model.addAttribute("storeStats",byStore); return "admin/dashboard";
    }
    @GetMapping("/catalogo") String catalog(Model model){ model.addAttribute("phones",phones.findAllDetailed()); model.addAttribute("brands",brands.findAll()); model.addAttribute("phoneForm",new AdminPhoneForm()); return "admin/catalog"; }
    @PostMapping("/catalogo") String save(@Valid @ModelAttribute("phoneForm") AdminPhoneForm form, BindingResult binding, Model model, RedirectAttributes redirect){
        if(binding.hasErrors()){ model.addAttribute("phones",phones.findAllDetailed()); model.addAttribute("brands",brands.findAll()); return "admin/catalog"; }
        admin.savePhone(form); redirect.addFlashAttribute("success","Equipo guardado correctamente"); return "redirect:/admin/catalogo";
    }
    @PostMapping("/catalogo/{id}/eliminar") String deletePhone(@PathVariable Long id){ admin.deletePhone(id); return "redirect:/admin/catalogo"; }
    @GetMapping("/usuarios") String users(Model model){ model.addAttribute("users",users.findAll()); return "admin/users"; }
    @PostMapping("/usuarios/{id}/estado") String toggleUser(@PathVariable Long id){ admin.toggleUser(id); return "redirect:/admin/usuarios"; }
    @PostMapping("/usuarios/{id}/eliminar") String deleteUser(@PathVariable Long id){ admin.deleteUser(id); return "redirect:/admin/usuarios"; }
}
