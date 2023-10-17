package com.app.colegio.Controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.colegio.Model.Docente;
import com.app.colegio.Repository.DocenteRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    private DocenteRepository docenteRepository;

    @GetMapping("/listar")
    public String listar(HttpSession session, Map<String, Object> model) {
        if (session.getAttribute("usuario") != null) {
            model.put("docentes", docenteRepository.findAll());
            return "docente/listarDocente";
        } else {
            return "redirect:/acceder";
        }
    }

    @GetMapping("/crear")
    public String crear(HttpSession session, Map<String, Object> model) {
        if (session.getAttribute("usuario") != null) {
            Docente docente = new Docente();
            model.put("docente", docente);
            return "docente/crearDocente";
        } else {
            return "redirect:/acceder";
        }
    }

    @PostMapping("/crear")
    public String guardar(HttpSession session, @Valid Docente docente, BindingResult bindingResult,
            Map<String, Object> model) {
        if (session.getAttribute("usuario") != null) {
            if (bindingResult.hasErrors()) {
                docente.setNombres(docente.getNombres().trim());
                docente.setApeMaterno(docente.getApeMaterno().trim());
                docente.setApePaterno(docente.getApePaterno().trim());
                model.put("docente", docente);
                return "docente/crearDocente";
            } else {
                docenteRepository.save(docente);
                return "redirect:/docente/listar";
            }

        } else {
            return "redirect:/acceder";
        }
    }

    @GetMapping("/actualizar/{id}")
    public String enviar(HttpSession session, @PathVariable String id, Map<String, Object> model) {
        if (session.getAttribute("usuario") != null) {
            try {
                Optional<Docente> docente = docenteRepository.findById(id);
                if (docente.isPresent()) {
                    model.put("docente", docente);
                    return "docente/editarDocente";
                } else {
                    return "redirect:/docente/listar";
                }
            } catch (Exception e) {
                return "redirect:/docente/listar";
            }
        } else {
            return "redirect:/docente/listar";
        }
    }

    @PostMapping("/actualizar")
    public String guardarCambios(HttpSession session, @Valid Docente docente, BindingResult bindingResult,
            Map<String, Object> model) {
        if (session.getAttribute("usuario") != null) {
            if (bindingResult.hasErrors()) {
                model.put("docente", docente);
                return "docente/editarDocente";
            } else {
                try {
                    Optional<Docente> docenteB = docenteRepository.findById(docente.getId());
                    if (docenteB.isPresent()) {
                        docenteRepository.save(docente);
                        return "redirect:/docente/listar";
                    } else {
                        return "docente/editarDocente";
                    }
                } catch (Exception e) {
                    model.put("docente", docente);
                    return "docente/editarDocente";
                }
            }
        } else {
            return "redirect:/docente/listar";
        }
    }
}
