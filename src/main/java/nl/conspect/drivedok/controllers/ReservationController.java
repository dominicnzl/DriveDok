package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.Reservation;
import nl.conspect.drivedok.model.ReservationDto;
import nl.conspect.drivedok.services.ReservationService;
import nl.conspect.drivedok.services.UserService;
import nl.conspect.drivedok.services.VehicleService;
import nl.conspect.drivedok.utilities.ReservationMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    public static final String RESERVATION_LISTPAGE = "reservation/reservation-listpage";

    public static final String RESERVATION_EDITPAGE = "reservation/reservation-editpage";

    private final ReservationService reservationService;

    private final ReservationMapper mapper;

    private final VehicleService vehicleService;

    private final UserService userService;

    public ReservationController(ReservationService reservationService,
                                 ReservationMapper mapper,
                                 VehicleService vehicleService,
                                 UserService userService) {
        this.reservationService = reservationService;
        this.mapper = mapper;
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView handleGet() {
        final var mav = new ModelAndView(RESERVATION_LISTPAGE);
        mav.addObject("reservations", reservationService.findAll());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView handleCreate() {
        final var mav = new ModelAndView(RESERVATION_EDITPAGE);
        mav.addObject("reservationDto", new ReservationDto());
        mav.addObject("users", userService.findAll());
        mav.addObject("vehicles", vehicleService.findAll());
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView handleGetId(@PathVariable Optional<Long> id) {
        var reservation = id.flatMap(reservationService::findById).orElseGet(Reservation::new);
        var dto = mapper.reservationToDto(reservation);
        final var mav = new ModelAndView(RESERVATION_EDITPAGE);
        mav.addObject("reservationDto", dto);
        mav.addObject("users", userService.findAll());
        mav.addObject("vehicles", vehicleService.findAll());
        return mav;
    }

    @DeleteMapping("/{id}")
    public ModelAndView handleDelete(@PathVariable Long id) {
        reservationService.deleteById(id);
        final var mav = new ModelAndView(RESERVATION_LISTPAGE);
        mav.addObject("reservations", reservationService.findAll());
        return mav;
    }

    @PostMapping
    public ModelAndView handlePost(@ModelAttribute ReservationDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(RESERVATION_EDITPAGE, bindingResult.getModel());
        }
        var entity = mapper.dtoToReservation(dto);
        reservationService.save(entity);
        var mav = new ModelAndView("redirect:/reservations");
        mav.addObject("reservations", reservationService.findAll());
        return mav;
    }
}
