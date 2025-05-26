package david.makao.service.impl;

import david.makao.service.PaymentSimulatorService;
import org.springframework.stereotype.Service;

@Service
public class PaymentSimulatorServiceImpl implements PaymentSimulatorService {

    @Override
    public boolean procesarPago(String cardNumber, double amount) {
        // Lógica simulada: si la tarjeta empieza con 4, pago aprobado
        return cardNumber != null && cardNumber.startsWith("4");
    }
}
