package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.services.ParkingSpotService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.lang.String.format;
import static nl.conspect.drivedok.model.ParkingType.possibleTypes;

@Controller
@RequestMapping("parkingspots")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    /* Thymeleaf controllers */
    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("parkingSpots", parkingSpotService.findAll());
        return "parkingspotlistpage";
    }

    @GetMapping("/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        var spot = parkingSpotService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("ParkingSpot with id %s not found", id)));
        model.addAttribute("parkingSpot", spot);
        model.addAttribute("parkingTypes", possibleTypes());
        return "parkingspoteditpage";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("parkingSpot", new ParkingSpot());
        model.addAttribute("parkingTypes", possibleTypes());
        return "parkingspotcreatepage";
    }

    @PostMapping("/save")
    public String saveOrUpdateParkingSpot(@ModelAttribute ParkingSpot parkingSpot,
                                          BindingResult bindingResult,
                                          Model model) {
        if (bindingResult.hasErrors()) {
            return "parkingspotcreatepage";
        }
        if (null == parkingSpot.getId() || parkingSpotService.findById(parkingSpot.getId()).isEmpty()) {
            parkingSpotService.create(parkingSpot);
        } else {
            parkingSpotService.update(parkingSpot);
        }
        model.addAttribute("parkingSpots", parkingSpotService.findAll());
        return "parkingspotlistpage";
    }

    @GetMapping("/delete/{id}")
    public String deleteAndReturnToListPage(@PathVariable Long id, Model model) {
        var spot = parkingSpotService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(format("ParkingSpot with id %s does not exist", id)));
        parkingSpotService.deleteById(id);
        model.addAttribute("parkingSpots", parkingSpotService.findAll());
        return "parkingspotlistpage";
    }

    /* JSON controllers */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParkingSpot>> findAll() {
        return ResponseEntity.ok(parkingSpotService.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingSpot> findById(@PathVariable Long id) {
        return ResponseEntity.of(parkingSpotService.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingSpot> create(@RequestBody ParkingSpot parkingSpot) {
        return ResponseEntity.ok(parkingSpotService.create(parkingSpot));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingSpot> update(@PathVariable Long id, @RequestBody ParkingSpot newParkingSpot) {
        return parkingSpotService.findById(id)
                .map(spot -> ResponseEntity.ok(parkingSpotService.update(newParkingSpot)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            parkingSpotService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
