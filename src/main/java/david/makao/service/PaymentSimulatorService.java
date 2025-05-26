package david.makao.service;

public interface PaymentSimulatorService {
    /**
     * Simula el procesamiento de un pago con tarjeta.
     *
     * @param cardNumber Número de tarjeta (string)
     * @param amount Monto a pagar
     * @return true si el pago fue aprobado (ej. tarjeta empieza con 4), false si es rechazado
     */
    boolean procesarPago(String cardNumber, double amount);
}
