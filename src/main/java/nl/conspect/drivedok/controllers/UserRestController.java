package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserList> findAllUsers() {
        var userList = new UserList(userService.findAll());
        return ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return of(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user, UriComponentsBuilder builder) {
        var createdUser = userService.save(user);
        var uri = builder.path("/api/users/{id}").buildAndExpand(createdUser.getId()).toUri();
        return created(uri).body(createdUser);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        return Optional.ofNullable(user)
                .map(User::getId)
                .flatMap(userService::findById)
                .map(u -> userService.update(u.getId(), user))
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return ok(userService.update(id, user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partiallyUpdate(@PathVariable Long id, @RequestBody Map<String, String> properties) {
        return ok(userService.updatePartially(id, properties));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return noContent().build();
    }

    static class UserList {
        private final List<User> users;

        public UserList(List<User> users) {
            this.users = users;
        }

        public final List<User> getUsers() {
            return users;
        }
    }
}
