package account.customExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User exist!") //
@Data
public class BadPasswordException extends RuntimeException{
    String message = "";

    public BadPasswordException() {
        super();

    }
}
