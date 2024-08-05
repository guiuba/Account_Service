package account.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RoleHandler {
    @NotEmpty
    private String user;
    private String role;
    private String operation;


}
