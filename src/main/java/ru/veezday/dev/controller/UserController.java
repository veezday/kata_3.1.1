package ru.veezday.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.veezday.dev.model.ROLE;
import ru.veezday.dev.model.User;
import ru.veezday.dev.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getUsers(@RequestParam(required = false) Long id, Model model) {
        User user = null;
        if (id != null) {
            user = userService.findById(id).orElse(null);
        }
        model.addAttribute("users", user == null ? userService.findAll() : List.of(user));
        return "index";
    }

    @PostMapping("/init")
    public RedirectView init() {
        userService.saveAll(List.of(
                new User("John", "Doe", "john@doe.com", ROLE.ADMIN),
                new User("Username", "UserLastname", "user@email.eu", ROLE.VISITOR),
                new User("Password", "Password", "password@email.com", ROLE.MENTOR),
                new User("Welcome", "ToSite", "academy@site.ru", ROLE.SUPPORT),
                new User("Admin", "Admin", "admin@email.com", ROLE.ADMIN),
                new User("Student", "NeverMind", "student@email.com", ROLE.STUDENT)
        ));
        return new RedirectView("/");
    }

    @PostMapping("/create")
    public RedirectView addUser(@RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String email,
                                @RequestParam String role) {
        userService.save(new User(name, surname, email, ROLE.valueOf(role)));
        return new RedirectView("/");
    }

    @PostMapping("/update")
    public RedirectView updateUser(@RequestParam Long id,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) String surname,
                                   @RequestParam(required = false) String email,
                                   @RequestParam String role) {
        User user = userService.findById(id).orElse(null);
        if (user != null) {
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setRole(ROLE.valueOf(role));
            userService.save(user);
        }

        return new RedirectView("/");
    }

    @PostMapping("/delete")
    public RedirectView deleteUser(@RequestParam Long id) {
        userService.deleteById(id);
        return new RedirectView("/");
    }
}
