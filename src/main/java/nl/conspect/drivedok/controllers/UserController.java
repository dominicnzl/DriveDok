package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.services.UserService;
import org.springframework.stereotype.Controller;
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
