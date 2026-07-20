package pe.celucheck;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CeluCheckWebTest {
    @Autowired MockMvc mvc;

    @Test void homeAndDetailArePublic() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"))
                .andExpect(model().attributeExists("products", "newProducts"));
        mvc.perform(get("/productos/iphone-15-pro-max-256gb")).andExpect(status().isOk()).andExpect(view().name("detail"))
                .andExpect(model().attributeExists("reviews", "reviewCount", "reviewAverage"));
    }

    @Test void profilesAndComparatorArePublic() throws Exception {
        mvc.perform(get("/").param("profile", "camera")).andExpect(status().isOk())
                .andExpect(model().attribute("selectedProfile", "camera"));
        mvc.perform(get("/catalogo").param("profile", "gaming")).andExpect(status().isOk())
                .andExpect(view().name("catalog")).andExpect(model().attribute("selectedProfile", "gaming"));
        mvc.perform(get("/comparar").param("ids", "1,2")).andExpect(status().isOk())
                .andExpect(view().name("compare")).andExpect(model().attributeExists("phones"));
    }

    @Test void favoritesRequireLogin() throws Exception {
        mvc.perform(get("/favoritos")).andExpect(status().is3xxRedirection());
    }

    @Test @WithMockUser(username="mirko@correo.com", roles="USER")
    void normalUserCannotOpenAdmin() throws Exception {
        mvc.perform(get("/admin")).andExpect(status().isForbidden());
    }

    @Test @WithMockUser(username="admin@celucheck.pe", roles="ADMIN")
    void adminPagesRender() throws Exception {
        mvc.perform(get("/admin")).andExpect(status().isOk()).andExpect(view().name("admin/dashboard"));
        mvc.perform(get("/admin/catalogo")).andExpect(status().isOk()).andExpect(view().name("admin/catalog"));
        mvc.perform(get("/admin/usuarios")).andExpect(status().isOk()).andExpect(view().name("admin/users"));
    }

    @Test void assistantReturnsARecommendation() throws Exception {
        mvc.perform(post("/api/chat").contentType(MediaType.APPLICATION_JSON).content("{\"message\":\"Busco buena batería\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.answer").isNotEmpty());
    }

    @Test void bcryptAdminCredentialsAuthenticate() throws Exception {
        mvc.perform(formLogin("/login").userParameter("email").user("admin@celucheck.pe").password("Admin123!"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"));
    }
}
