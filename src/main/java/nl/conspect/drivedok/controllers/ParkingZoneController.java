package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ParkingZone;
import nl.conspect.drivedok.services.ParkingZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("parkingzone")
public class ParkingZoneController {

    private final ParkingZoneService parkingZoneService;

    public ParkingZoneController(ParkingZoneService parkingZoneService) {
        this.parkingZoneService = parkingZoneService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model){
        model.addAttribute("parkingzones", parkingZoneService.findAll());
        return "homepage";
    }

    @GetMapping("/create")
    public String showParkingZoneForm(Model model, ParkingZone parkingZone) {
        model.addAttribute("parkingZone", parkingZone);
        return "createparkingzoneform";
    }

    @PostMapping("/create")
    public String saveParkingZone(Model model, @Valid ParkingZone parkingZone,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "createparkingzoneform";
        }
        model.addAttribute("parkingZone", parkingZone);
        parkingZoneService.create(parkingZone);
        return "parkingzone";
    }

    @PostMapping("/update")
    public String updateParkingZone(Model model, ParkingZone parkingZone){
        parkingZoneService.update(parkingZone);
        return "parkingzone";
    }

    @GetMapping  ("/delete/{id}")
    public String deleteParkingZone(Model model, @PathVariable Long id){
        parkingZoneService.deleteById(id);
        model.addAttribute("parkingzones", parkingZoneService.findAll());
        return "homepage";
    }

    @GetMapping("/{id}")
    public String showSingleParkingZone(Model model, @PathVariable long id) {
        var parkingZone = parkingZoneService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("ParkingZone with id %s not found", id)));
        model.addAttribute("parkingZone", parkingZone);
        return "parkingzone";
    }
}
