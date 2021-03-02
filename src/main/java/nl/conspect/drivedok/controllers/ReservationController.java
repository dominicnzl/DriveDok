package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.services.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView listpage() {
        var mav = new ModelAndView("reservation-listpage");
        mav.addObject("reservations", service.findAll());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView editpage(@PathVariable Long id) {
        var mav = new ModelAndView("reservation-editpage");
        final var reservation = service.findById(id).orElseGet(Reservation::new);
        mav.addObject("reservation", reservation);
        return mav;
    }
}
