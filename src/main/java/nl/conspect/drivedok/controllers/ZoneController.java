package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Zone;
import nl.conspect.drivedok.services.ZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping
    public String showHomePage(Model model){
        model.addAttribute("zones", zoneService.findAll());
        return "zonehome";
    }

    @GetMapping("/create")
    public String showZoneForm(Model model, Zone zone) {
        model.addAttribute("zone", zone);
        return "zonecreateform";
    }

    @PostMapping("/create")
    public String saveZone(Model model, @Valid Zone zone,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "zonecreateform";
        }
        zoneService.create(zone);
        model.addAttribute("zone", zone);
        return "zone";
    }

    @PostMapping("/update")
    public String updateZone(Model model,@Valid Zone zone,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "zone";
        }
        zoneService.update(zone);
        model.addAttribute("zone", zone);
        return "zone";
    }

    @GetMapping  ("/delete/{id}")
    public String deleteZone(Model model, @PathVariable Long id){
        zoneService.deleteById(id);
        model.addAttribute("zones", zoneService.findAll());
        return "zonehome";
    }

    @GetMapping("/{id}")
    public String showSingleZone(Model model, @PathVariable long id) {
        var zone = zoneService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Zone with id %s not found", id)));
        model.addAttribute("zone", zone);
        return "zone";
    }
}
