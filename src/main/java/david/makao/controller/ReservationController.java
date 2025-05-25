package david.makao.controller;

import david.makao.model.ReservationEntity;
import david.makao.model.UserEntity;
import david.makao.service.ReservationService;
import david.makao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @GetMapping("/reservas")
    public String verReservasUsuario(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserEntity usuario = userService.findByEmail(userDetails.getUsername());
        List<ReservationEntity> reservas = reservationService.findByUser(usuario);
        model.addAttribute("reservas", reservas);
        return "mis-reservas";
    }
}
