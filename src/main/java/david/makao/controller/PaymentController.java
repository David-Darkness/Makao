package david.makao.controller;

import david.makao.service.PaymentSimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador que maneja las solicitudes relacionadas con el proceso de pago.
 * Solo accesible para usuarios con roles ADMIN, COLLABORATOR o CUSTOMER.
 *
 * <p>Simula el procesamiento de un pago y pasa los datos necesarios a la vista de resultado.</p>
 *
 * @author David
 * @version 1.0
 */
@Controller
@PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR','CUSTOMER')")
public class PaymentController {

    @Autowired
    private PaymentSimulatorService paymentSimulatorService;

    /**
     * Procesa los datos del formulario de pago y devuelve una vista con el resultado.
     *
     * @param cardNumber        Número de tarjeta ingresado por el usuario
     * @param totalPrice        Precio total a pagar
     * @param buyerEmail        Correo electrónico del comprador
     * @param tourPackageId     ID del paquete turístico seleccionado
     * @param hotelId           ID del hotel seleccionado
     * @param restaurantId      ID del restaurante seleccionado
     * @param startDate         Fecha de inicio del viaje
     * @param numberOfPeople    Cantidad de personas que viajarán
     * @param model             Objeto para enviar datos a la vista
     * @return Nombre de la vista "pago-resultado"
     */
    @PostMapping("/pago")
    public String procesarPago(
            @RequestParam String cardNumber,
            @RequestParam double totalPrice,
            @RequestParam String buyerEmail,
            @RequestParam Long tourPackageId,
            @RequestParam Long hotelId,
            @RequestParam Long restaurantId,
            @RequestParam String startDate,
            @RequestParam int numberOfPeople,
            Model model) {

        // Simular proceso de pago
        boolean aprobado = paymentSimulatorService.procesarPago(cardNumber, totalPrice);

        // Agregar atributos básicos y de reserva al modelo
        model.addAttribute("aprobado", aprobado);
        model.addAttribute("amount", totalPrice);
        model.addAttribute("tourPackageId", tourPackageId);
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("numberOfPeople", numberOfPeople);
        model.addAttribute("totalPrice", totalPrice);

        return "pago-resultado";
    }
}
