package br.com.kenzley.fiap.service.payment.infrastructure.exceptions;

public class MercadoPagoException extends RuntimeException {
    public MercadoPagoException(String message) {
        super(message);
    }
}
