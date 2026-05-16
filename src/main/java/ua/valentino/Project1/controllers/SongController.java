package ua.valentino.Project1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;
import ua.valentino.Project1.services.*;
@Controller
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @GetMapping("/songs")
    public String index(Model model) {
        //Список усіх жанрів
        var list = songService.getAllSongs();
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        // System.out.print(list.size());
        System.out.println(list.get(0).getName());
        model.addAttribute("songs", list);
        return "songs/index";
    }
}