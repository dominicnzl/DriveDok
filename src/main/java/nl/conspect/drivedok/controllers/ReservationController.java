package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import nl.conspect.drivedok.services.ReservationService;
import nl.conspect.drivedok.utilities.ReservationMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    private final ReservationMapper mapper;

    public ReservationController(ReservationService service, ReservationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ModelAndView listpage() {
        final var mav = new ModelAndView("reservation-listpage");
        mav.addObject("reservations", service.findAll());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView editpage(@PathVariable Long id) {
        final var mav = new ModelAndView("reservation-editpage");
        final var reservation = service.findById(id).orElseGet(Reservation::new);
        mav.addObject("reservation", reservation);
        return mav;
    }

    @PostMapping
    public ModelAndView save(@ModelAttribute ReservationDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return editpage(dto.getId());
        }
        var entity = mapper.dtoToReservation(dto);
        service.save(entity);
        return listpage();
    }
}
