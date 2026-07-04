package com.ebdv.ebdv_fdv.controller;

import com.ebdv.ebdv_fdv.model.Asistencia;
import com.ebdv.ebdv_fdv.model.Nino;
import com.ebdv.ebdv_fdv.model.Tutor;
import com.ebdv.ebdv_fdv.repository.NinoRepository;
import com.ebdv.ebdv_fdv.repository.TutorRepository;
import com.ebdv.ebdv_fdv.repository.AsistenciaRepository;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AppController {

    @Autowired
    private NinoRepository ninoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @GetMapping("/")
    public String inicio(Model model) {

        List<Nino> listaNinos = ninoRepository.findAll();

        List<Nino> ninosOrdenados = listaNinos.stream().filter(Nino::getActivo)
                .sorted((n1,n2) -> n1.getNombreCompleto().compareToIgnoreCase(n2.getNombreCompleto()))
                .collect(Collectors.toList());

        long totalInscritos = ninosOrdenados.size();
        long totalPreescolares = ninosOrdenados.stream()
                        .filter(n -> n.getGrupo() != null && n.getGrupo().equalsIgnoreCase("Preescolares")).count();

        long totalEscolares = ninosOrdenados.stream()
                .filter(n -> n.getGrupo() != null && n.getGrupo().equalsIgnoreCase("Escolares")).count();

        long totalJovenes = ninosOrdenados.stream()
                .filter(n -> n.getGrupo() != null && n.getGrupo().equalsIgnoreCase("Jóvenes")).count();

        model.addAttribute("ninos", ninosOrdenados);
        model.addAttribute("totalInscritos", totalInscritos);
        model.addAttribute("totalPreescolares", totalPreescolares);
        model.addAttribute("totalEscolares", totalEscolares);
        model.addAttribute("totalJovenes", totalJovenes);

        return "inicio";
    }

    @GetMapping("/registro")
    public String registro(Model model) {
        Nino nino = new Nino();
        nino.setTutor(new Tutor());

        model.addAttribute("nino", nino);
        return "registro";
    }

    @PostMapping("/registro/guardar")
    public String guardarnino(@ModelAttribute("nino") Nino nino, RedirectAttributes redirectAttributes) {
        try{
            Tutor tutorGuardado = tutorRepository.save(nino.getTutor());
            nino.setTutor(tutorGuardado);
            ninoRepository.save(nino);

            redirectAttributes.addFlashAttribute("registroExitoso", true);
            redirectAttributes.addFlashAttribute("mensaje", "El alumno "+ nino.getNombreCompleto() + "ha sido registrado con exito");

            return "redirect:/";

        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "hubo un problema al guardar el registro");
            return "inicio";
        }
    }

    @PostMapping("/registro/archivar/{id}")
    public String archivarNino(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Nino nino = ninoRepository.findById(id).orElse(null);
        if (nino != null) {
            nino.setActivo(false);
            ninoRepository.save(nino);
            redirectAttributes.addFlashAttribute("registroExitoso", true);
            redirectAttributes.addFlashAttribute("mensaje", "El alumno fue dado de baja correctamente");
        }
        return "redirect:/inicio";
    }

    @GetMapping("/registro/editar/{id}")
    public String formEditar(@PathVariable("id") Long id, Model model) {
        Nino nino = ninoRepository.findById(id).orElse(null);
        if (nino == null) {
            return "redirect:/inicio";
        }
        model.addAttribute("nino", nino);
        return "registro";
    }

    @GetMapping("/asistencia")
    public String asistencia(Model model) {
        List<Nino> ninosOrdenados = ninoRepository.findAll().stream().
                filter(Nino::getActivo)
                .sorted((n1, n2) -> n1.getNombreCompleto().compareToIgnoreCase(n2.getNombreCompleto()))
                .collect(Collectors.toList());
        model.addAttribute("ninos", ninosOrdenados);
        return "asistencia";
    }

    @PostMapping("/asistencia/guardar")
    public String guardarAsistencia(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        asistenciaRepository.deleteAll();

        for(String paramName : parameterMap.keySet()) {
            if (paramName.startsWith("asistencia_")) {
                String[] partes = paramName.split("_");
                Long ninoId = Long.parseLong(partes[1]);
                String diaSemana = partes[2];

                Nino nino = ninoRepository.findById(ninoId).orElse(null);
                if(nino != null) {
                    Asistencia nuevaAsistencia = new Asistencia(nino, diaSemana, true);
                    asistenciaRepository.save(nuevaAsistencia);
                }
            }
        }
        redirectAttributes.addFlashAttribute("registroExitoso", true);
        redirectAttributes.addFlashAttribute("mensaje", "El control de asistencia se actualizo correctamente");

        return "redirect:/asistencia";
    }


}
