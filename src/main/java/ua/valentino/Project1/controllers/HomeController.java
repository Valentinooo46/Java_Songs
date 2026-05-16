package ua.valentino.Project1.controllers;

import lombok.RequiredArgsConstructor;
import ua.valentino.Project1.repositories.IGenreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final IGenreRepository genreRepository;

    @GetMapping("/")
    public String index(Model model) {
        // Список усіх жанрів
        var list = genreRepository.findAll();
        model.addAttribute("genres", list);
        return "index";
    }
}
