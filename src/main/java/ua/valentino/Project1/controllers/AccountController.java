package ua.valentino.Project1.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.valentino.Project1.dtos.account.RegisterDto;
import ua.valentino.Project1.services.AccountService;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "account/register";
    }

    @PostMapping("/register")
    public String register(RegisterDto dto, Model model) {
        try {
            accountService.register(dto);
            return "redirect:/login?success=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerDto", dto);
            return "account/register";
        }
    }
}
