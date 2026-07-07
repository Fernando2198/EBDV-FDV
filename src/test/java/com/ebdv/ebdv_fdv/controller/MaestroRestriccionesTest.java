package com.ebdv.ebdv_fdv.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class MaestroRestrictionsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void maestroIntentaAccederARegistro_DebeRetornarProhibido() throws Exception {
        var auth = new UsernamePasswordAuthenticationToken(
                "maestro@correo.com",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_MAESTRO"))
        );

        mockMvc.perform(get("/registro")
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(view().name("registro"))
                .andExpect(content().string(containsString("Acceso No Autorizado"))); // Opcional: valida el texto
    }
}
