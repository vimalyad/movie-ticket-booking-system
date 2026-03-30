package dto;

public class PaymentRequest {
    private String paymentMethod;
    private String paymentToken;

    public PaymentRequest(String paymentMethod, String paymentToken) {
        this.paymentMethod = paymentMethod;
        this.paymentToken = paymentToken;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentToken() {
        return paymentToken;
    }
}