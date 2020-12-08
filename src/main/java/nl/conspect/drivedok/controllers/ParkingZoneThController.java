package nl.conspect.drivedok.controllers;


import nl.conspect.drivedok.services.ParkingZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/th")
public class ParkingZoneThController {

    private final ParkingZoneService parkingZoneService;

    public ParkingZoneThController(ParkingZoneService parkingZoneService) {
        this.parkingZoneService = parkingZoneService;
    }

    @GetMapping("/")
    public String helloWorld(){
        return "helloWorld";
    }

    @GetMapping("/findall")
    public String findAllParkingZones(Model model){
        model.addAttribute("parkingzones", parkingZoneService.findAll());
        return "all-parkingzones";
    }

}
