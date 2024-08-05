package account.customExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The passwords must be different!")
@Data
public class SamePasswordException extends RuntimeException{
    public static String message = "The passwords must be different!";
    public SamePasswordException() {

        super();
    }
}
