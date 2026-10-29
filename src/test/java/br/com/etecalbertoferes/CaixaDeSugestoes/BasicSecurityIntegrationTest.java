package br.com.etecalbertoferes.CaixaDeSugestoes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class BasicSecurityIntegrationTest extends ContextTest {
    @Autowired MockMvc mvc;
    @Test void publicReadsRemainAccessible() throws Exception {
        for (String path : new String[]{"/api/suggestions", "/api/courses", "/api/categories", "/v3/api-docs"})
            mvc.perform(get(path)).andExpect(status().isOk());
    }
    @Test void anonymousCanSubmitWithoutCsrfOrLogin() throws Exception {
        mvc.perform(post("/api/suggestions").contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isBadRequest()).andExpect(jsonPath("$.fieldErrors.title").exists());
    }
    @Test void adminOperationsRequireAuthentication() throws Exception {
        mvc.perform(delete("/api/suggestions/999999")).andExpect(status().isUnauthorized())
            .andExpect(header().string("WWW-Authenticate", "Basic realm=\"administracao\""))
            .andExpect(jsonPath("$.status").value(401)).andExpect(jsonPath("$.fieldErrors").isEmpty());
        mvc.perform(put("/api/suggestions/999999")).andExpect(status().isUnauthorized());
    }
    @Test void basicAuthenticatesOnlyConfiguredAdmin() throws Exception {
        mvc.perform(delete("/api/suggestions/999999").with(httpBasic("test-admin", ADMIN_PASSWORD)))
            .andExpect(status().isNotFound());
        mvc.perform(delete("/api/suggestions/999999").with(httpBasic("test-admin", "wrong")))
            .andExpect(status().isUnauthorized());
    }
    @Test void inactiveAdminCannotAuthenticate() throws Exception {
        jdbc.update("update administrators set active=false where username=?", "test-admin");
        try {
            mvc.perform(delete("/api/suggestions/999999").with(httpBasic("test-admin", ADMIN_PASSWORD)))
                .andExpect(status().isUnauthorized());
        } finally { jdbc.update("update administrators set active=true where username=?", "test-admin"); }
    }
    @Test void authenticatedNonAdminGetsForbidden() throws Exception {
        mvc.perform(delete("/api/suggestions/999999").with(user("reader").roles("READER")))
            .andExpect(status().isForbidden()).andExpect(jsonPath("$.status").value(403));
    }
}
