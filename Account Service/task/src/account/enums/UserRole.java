package account.enums;

import java.util.ArrayList;
import java.util.List;

public enum UserRole {
    ADMINISTRATOR("ROLE_ADMINISTRATOR"), ACCOUNTANT("ROLE_ACCOUNTANT"), USER("ROLE_USER");

    private final String userRole;


    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

   public static List<String> rolesList() {
        List<String> listOfRoles = new ArrayList<>();
        for (UserRole value : values()) {
           listOfRoles.add(value.getUserRole().substring(5));
        }
        return listOfRoles;
    }
}
