package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.DriveDokSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.services.DriveDokSpotService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("drivedokspots")
public class DriveDokSpotController {

    private final DriveDokSpotService driveDokSpotService;

    public DriveDokSpotController(DriveDokSpotService driveDokSpotService) {
        this.driveDokSpotService = driveDokSpotService;
    }

    /* Thymeleaf controllers */
    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("driveDokSpots", driveDokSpotService.findAll());
        return "drivedokspotlistpage";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        var spot = driveDokSpotService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("DriveDokSpot with id {} not found", id)));
        model.addAttribute("driveDokSpot", spot);
        var types = Arrays.asList(ParkingType.values());
        model.addAttribute("parkingTypes", types);
        return "drivedokspoteditpage";
    }

    @PostMapping
    public String saveOrUpdateDriveDokSpot(@ModelAttribute DriveDokSpot driveDokSpot, Model model) {
        // TODO: 21/12/2020 add BindingResult errors
        if (null == driveDokSpot.getId() || driveDokSpotService.findById(driveDokSpot.getId()).isEmpty()) {
            driveDokSpotService.create(driveDokSpot);
        } else {
            driveDokSpotService.update(driveDokSpot);
        }
        model.addAttribute("driveDokSpot", driveDokSpot);
        return "drivedokspotlistpage";
    }

    /* json controllers */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DriveDokSpot>> findAll() {
        return ResponseEntity.ok(driveDokSpotService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriveDokSpot> findById(@PathVariable Long id) {
        return ResponseEntity.of(driveDokSpotService.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriveDokSpot> create(@RequestBody DriveDokSpot driveDokSpot) {
        return ResponseEntity.ok(driveDokSpotService.create(driveDokSpot));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriveDokSpot> update(@PathVariable Long id, @RequestBody DriveDokSpot newDriveDokSpot) {
        return driveDokSpotService.findById(id)
                .map(driveDokSpot -> ResponseEntity.ok(driveDokSpotService.update(newDriveDokSpot)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            driveDokSpotService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
