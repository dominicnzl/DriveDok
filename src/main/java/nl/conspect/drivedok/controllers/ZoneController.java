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
@RequestMapping("/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    private static final String LIST = "/zone/zone-listpage";

    private static final String EDIT = "/zone/zone-editpage";

    private static final String FORM = "/zone/zone-form";

    @GetMapping
    public String showAllZones(Model model) {
        model.addAttribute("zones", zoneService.findAll());
        return LIST;
    }

    @GetMapping("/create")
    public String showZoneForm(Model model, Zone zone) {
        model.addAttribute("zone", zone);
        return FORM;
    }

    @PostMapping("/create")
    public String saveZone(Model model, @Valid Zone zone,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return FORM;
        }
        zoneService.create(zone);
        model.addAttribute("zone", zone);
        return EDIT;
    }

    @PostMapping("/update")
    public String updateZone(Model model, @Valid Zone zone, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            zoneService.update(zone);
            model.addAttribute("zone", zone);
        }
        return EDIT;
    }

    @GetMapping("/delete/{id}")
    public String deleteZone(Model model, @PathVariable Long id) {
        zoneService.deleteById(id);
        model.addAttribute("zones", zoneService.findAll());
        return LIST;
    }

    @GetMapping("/{id}")
    public String showSingleZone(Model model, @PathVariable long id) {
        var zone = zoneService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Zone with id %s not found", id)));
        model.addAttribute("zone", zone);
        return EDIT;
    }
}
