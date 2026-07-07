package com.ebdv.ebdv_fdv.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityAndLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void usuarioAnonimo_DebeSerRedirigidoAlLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void loginConCredencialesCorrectas_DebeAutenticarYRedirigir() throws Exception {

        FormLoginRequestBuilder loginRequest = formLogin("/login")
                .user("admin@ejemplo.com")
                .password("admin123");

        mockMvc.perform(loginRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("admin@ejemplo.com"));
    }

    @Test
    void loginConCredencialesIncorrectas_DebeFallar() throws Exception {
        FormLoginRequestBuilder loginRequest = formLogin("/login")
                .user("usuario_falso")
                .password("clave_erronea");

        mockMvc.perform(loginRequest)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }
}
