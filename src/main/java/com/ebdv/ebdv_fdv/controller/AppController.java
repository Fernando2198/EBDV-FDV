package com.ebdv.ebdv_fdv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/inicio")
    public String inicio() {
        return "inicio";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro";
    }

    @GetMapping("/asistencia")
    public String asistencia() {
        return "asistencia"; }
}
