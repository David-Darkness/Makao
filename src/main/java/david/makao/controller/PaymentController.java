package david.makao.controller;

import david.makao.dto.PaymentRequest;
import david.makao.service.PaymentSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR','CUSTOMER')")
public class PaymentController {

    @Autowired
    private PaymentSimulatorService paymentSimulatorService;

    // Procesar formulario
    @PostMapping("/pago")
    public String procesarPago(
            // Datos del pago (tarjeta)
            @RequestParam String cardNumber,
            @RequestParam double totalPrice,
            @RequestParam String buyerEmail,

            // Datos de la reserva (¡agregados!)
            @RequestParam Long tourPackageId,
            @RequestParam Long hotelId,
            @RequestParam Long restaurantId,
            @RequestParam String startDate,
            @RequestParam int numberOfPeople,

            Model model) {

        // 1. Procesar el pago
        boolean aprobado = paymentSimulatorService.procesarPago(cardNumber, totalPrice);

        // 2. Atributos básicos del resultado
        model.addAttribute("aprobado", aprobado);
        model.addAttribute("amount", totalPrice);

        // 3. Atributos críticos para la confirmación (¡Nuevos!)
        model.addAttribute("tourPackageId", tourPackageId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("numberOfPeople", numberOfPeople);
        model.addAttribute("totalPrice", totalPrice); // Ya existía pero se incluye por claridad

        return "pago-resultado";
    }
}

