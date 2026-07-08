package com.ebdv.ebdv_fdv.config;

import com.ebdv.ebdv_fdv.model.Rol;
import com.ebdv.ebdv_fdv.model.Usuario;
import com.ebdv.ebdv_fdv.repository.RolRepository;
import com.ebdv.ebdv_fdv.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.security.admin.user:admin@ejemplo.com}")
    private String adminUser;

    @Value("${app.security.admin.pass:admin123}")
    private String adminPass;

    @Value("${app.security.maestro.user:maestro@ejemplo.com}")
    private String maestroUser;

    @Value("${app.security.maestro.pass:maestro123}")
    private String maestroPass;

    public DataInitializer(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Crear Roles si no existen
        Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                .orElseGet(() -> rolRepository.save(new Rol("ROLE_ADMIN")));

        Rol rolMaestro = rolRepository.findByNombre("ROLE_MAESTRO")
                .orElseGet(() -> rolRepository.save(new Rol("ROLE_MAESTRO")));

        // 2. Crear o Actualizar Administrador
        Usuario admin = usuarioRepository.findByUsername(adminUser).orElseGet(Usuario::new);
        admin.setUsername(adminUser);
        admin.setPassword(passwordEncoder.encode(adminPass));
        admin.setRoles(Set.of(rolAdmin));
        usuarioRepository.save(admin);

        // 3. Crear o Actualizar Maestro
        Usuario maestro = usuarioRepository.findByUsername(maestroUser).orElseGet(Usuario::new);
        maestro.setUsername(maestroUser);
        maestro.setPassword(passwordEncoder.encode(maestroPass));
        maestro.setRoles(Set.of(rolMaestro));
        usuarioRepository.save(maestro);

        System.out.println("Usuarios por defecto sincronizados correctamente.");
    }
}
