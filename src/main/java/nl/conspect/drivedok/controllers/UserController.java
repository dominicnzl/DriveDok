package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* Thymeleaf controllers */
    @GetMapping
    public String listPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userlistpage";
    }

    @GetMapping("/{id}")
    public String editPage(Model model, @PathVariable Long id) {
        var user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("User with %s not found", id)));
        model.addAttribute("user", user);
        model.addAttribute("vehicles", user.getVehicles());
        return "usereditpage";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("user", new User());
        return "usercreatepage";
    }

    @PostMapping("/save")
    public String save(Model model, @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "usereditpage";
        }
        if (null == user.getId() || userService.findById(user.getId()).isEmpty()) {
            userService.create(user);
        } else {
            userService.update(user);
        }
        model.addAttribute("users", userService.findAll());
        return "userlistpage";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id) {
        userService.findById(id).ifPresent(userService::delete);
        model.addAttribute("users", userService.findAll());
        return "userlistpage";
    }

    /* Json controllers */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User findById(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("User with id %s does not exist.", id)));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.findById(id)
                .map(u -> userService.update(user))
                .orElseThrow(() -> new IllegalArgumentException(format("User with id %s does not exist.", id)));
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
