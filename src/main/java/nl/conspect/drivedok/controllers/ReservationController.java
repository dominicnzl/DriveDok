package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.exceptions.ReservationNotFoundException;
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

    public static final String LISTPAGE = "reservation-listpage";

    public static final String EDITPAGE = "reservation-editpage";

    private final ReservationService service;

    private final ReservationMapper mapper;

    public ReservationController(ReservationService service, ReservationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ModelAndView handleGet() {
        final var mav = new ModelAndView(LISTPAGE);
        mav.addObject("reservations", service.findAll());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView handleCreate() {
        final var mav = new ModelAndView(EDITPAGE);
        mav.addObject("reservation", new Reservation());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView handleGetId(@PathVariable Long id) {
        final var reservation = id == null
                ? new Reservation()
                : service.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
        final var mav = new ModelAndView(EDITPAGE);
        mav.addObject("reservation", reservation);
        return mav;
    }

    @PostMapping
    public ModelAndView handlePost(@ModelAttribute ReservationDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return handleGetId(dto.getId());
        }
        var entity = mapper.dtoToReservation(dto);
        service.save(entity);
        return handleGet();
    }
}
