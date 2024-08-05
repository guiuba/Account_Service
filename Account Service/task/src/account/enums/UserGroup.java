package account.enums;

import java.util.EnumSet;

public enum UserGroup {
    ADMINISTRATIVE(EnumSet.of(UserRole.ADMINISTRATOR)),
    BUSINESS_USERS(EnumSet.of(UserRole.USER, UserRole.ACCOUNTANT));

/*  UserGroup(EnumSet<UserRole> userRoles) {
      u = userRoles;
  }*/

    private final EnumSet<UserRole> roleSet;

    private UserGroup(EnumSet<UserRole> roleSet) {
        this.roleSet = roleSet;
    }

    public static boolean isAdministrative(UserRole userRole) {
        return ADMINISTRATIVE.roleSet.contains(userRole);
    }
}
