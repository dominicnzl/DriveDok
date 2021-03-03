package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.UserDto;
import nl.conspect.drivedok.services.UserService;
import nl.conspect.drivedok.utilities.UserMapper;
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

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    private final UserMapper mapper;

    public UserRestController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return of(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto dto) {
        var entity = userService.save(mapper.dtoToUser(dto));
        var location = URI.create("/api/users/" + entity.getId());
        return created(location).body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserDto dto) {
        if (userService.findById(id).isEmpty()) {
            return notFound().build();
        }
        var user = mapper.dtoToUser(dto);
        user.setId(id);
        return ok(userService.save(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePartially(@PathVariable Long id, @RequestBody UserDto dto) {
        return userService.findById(id)
                .map(e -> mapper.patchDtoToUser(dto, e))
                .map(userService::save)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return userService.findById(id)
                .map(entity -> {
                    userService.delete(entity);
                    return noContent().<Void>build();
                })
                .orElseGet(() -> notFound().build());
    }
}
