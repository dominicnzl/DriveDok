package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.ZoneDto;
import nl.conspect.drivedok.services.ZoneService;
import nl.conspect.drivedok.utilities.ZoneMapper;
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

    private final ZoneMapper zoneMapper;

    private static final String LIST = "/zone/zone-listpage";

    private static final String EDIT = "/zone/zone-editpage";

    private static final String FORM = "/zone/zone-form";
    
    private static final String DTO = "zoneDto";

    public ZoneController(ZoneService zoneService, ZoneMapper zoneMapper) {
        this.zoneService = zoneService;
        this.zoneMapper = zoneMapper;
    }

    @GetMapping
    public String showAllZones(Model model) {
        model.addAttribute("zones", zoneService.findAll());
        return LIST;
    }

    @GetMapping("/create")
    public String showZoneForm(Model model, ZoneDto dto) {
        model.addAttribute(DTO, dto);
        return FORM;
    }

    @PostMapping("/create")
    public String saveZone(Model model, @Valid ZoneDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(bindingResult.getModel());
            return FORM;
        }
        var zone = zoneMapper.dtoToZone(dto);
        zoneService.create(zone);
        dto.setId(zone.getId());
        model.addAttribute(DTO, dto);
        return "redirect:/zones";
    }

    @PostMapping("/update")
    public String updateZone(Model model, @Valid ZoneDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(bindingResult.getModel());
        } else {
            var zone = zoneMapper.dtoToZone(dto);
            zoneService.update(zone);
            dto.setId(zone.getId());
            model.addAttribute(DTO, dto);
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
        var dto = zoneMapper.zoneToDto(zone);
        model.addAttribute(DTO, dto);
        return EDIT;
    }
}
