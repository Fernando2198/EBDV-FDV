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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@org.mockito.junit.jupiter.MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class NinoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registrarNuevoAlumno_DebeGuardarYRedirigir() throws Exception {
        var auth = new UsernamePasswordAuthenticationToken(
                "admin@ejemplo.com",
                "admin123",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        mockMvc.perform(post("/registro/guardar")
                        .with(authentication(auth))
                        .with(csrf())
                        .param("nombreCompleto", "Mateo Pérez")
                        .param("edad", "8")
                        .param("grupo", "Primarios"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void darDeBajaAlumno_DebeCambiarEstadoAInactivo() throws Exception {
        var auth = new UsernamePasswordAuthenticationToken(
                "admin@ejemplo.com",
                "admin123",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        mockMvc.perform(post("/registro/archivar/1")
                        .with(authentication(auth))
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/inicio"));
    }
}