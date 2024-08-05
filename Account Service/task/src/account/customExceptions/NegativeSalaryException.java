package account.customExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Salary must be non negative!")
@Data
public class NegativeSalaryException extends RuntimeException{
    public static String message = "Salary must be non negative!";

    public NegativeSalaryException() {
    }
}
