package account.customExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@Data
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Unauthorized") // HttpStatus.UNAUTHORIZED
public class UnauthorizedException extends RuntimeException{
    public static String message = "";
}
