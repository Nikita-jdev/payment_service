package faang.school.paymentservice.service.exception;

public class CurrencyNotFoundException extends RuntimeException{
    public CurrencyNotFoundException(String message){
        super(message);
    }
}
