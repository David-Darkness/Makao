package david.makao.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentSimulatorService {

    public boolean procesarPago(String cardNumber, double amount) {
        // Lógica simulada: si la tarjeta empieza con 4, pago aprobado
        return cardNumber != null && cardNumber.startsWith("4");
    }
}
