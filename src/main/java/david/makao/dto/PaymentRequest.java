package david.makao.dto;

public class PaymentRequest {
    private String cardNumber;
    private double amount;
    private String buyerEmail;
    // Constructor vacío, getters y setters

    public PaymentRequest() {}

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getBuyerEmail() { return buyerEmail; }
    public void setBuyerEmail(String buyerEmail) { this.buyerEmail = buyerEmail; }
}
