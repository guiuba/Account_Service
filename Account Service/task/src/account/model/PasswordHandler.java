package account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PasswordHandler {
    @JsonProperty("new_password")
    private String new_password;
}
