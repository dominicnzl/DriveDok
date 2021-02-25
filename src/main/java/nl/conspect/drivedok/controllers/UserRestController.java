package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.UserDto;
import nl.conspect.drivedok.services.UserService;
import nl.conspect.drivedok.utilities.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.of;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService service;

    private final UserMapper mapper;

    public UserRestController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ok(mapper.usersToDtos(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return of(service.findById(id).map(mapper::userToDto));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        var entity = service.save(mapper.dtoToUser(dto));
        return status(CREATED).body(mapper.userToDto(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        if (service.findById(id).isEmpty()) {
            return notFound().build();
        }
        var user = mapper.dtoToUser(dto);
        user.setId(id);
        var entity = service.save(user);
        return ok(mapper.userToDto(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) {
            return notFound().build();
        }
        service.deleteById(id);
        return noContent().build();
    }
}
