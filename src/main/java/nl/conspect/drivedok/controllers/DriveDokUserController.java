package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.DriveDokUser;
import nl.conspect.drivedok.services.DriveDokUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class DriveDokUserController {

    private final DriveDokUserService driveDokUserService;

    public DriveDokUserController(DriveDokUserService driveDokUserService) {
        this.driveDokUserService = driveDokUserService;
    }

    @GetMapping("/users")
    public Collection<DriveDokUser> findAllUsers() {
        return driveDokUserService.findAll();
    }





}
