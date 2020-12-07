package nl.conspect.drivedok.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/th")
public class ParkingZoneThController {

    @GetMapping("/")
    public String helloWorld(){
        return "helloWorld";
    }

    @GetMapping("/findAll")
    public String findAllParkingZones(){
        return "";
    }

}
