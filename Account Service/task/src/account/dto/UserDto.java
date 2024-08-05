package account.dto;

//import jakarta.validation.constraints.NotEmpty;

import account.enums.UserRole;
import account.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private long id;
    private String name;
    private String lastname;
    private String email;
    private List<String> roles; //Group
// @SortNatural

/* public List<String> getUserGroups() {
     return userGroups.stream().map(Group::getName).sorted().toList();
 }*/

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail().toLowerCase();
        this.roles = user.getRoles().stream().map(UserRole::getUserRole).sorted().toList();
      //  this.roles = user.getRoles().stream().map(Role -> Role.getUserRole().name()).sorted().toList();
        /*this.roles = user.getUserGroups().stream().map(Group::getName).sorted().toList();*/
    }

    @Override
    public String toString() {
        return "{" +
                "id " + id +
                ", name " + name  +
                ", lastname " + lastname  +
                ", email " + email +
                ", userGroups " + roles +
                '}';
    }
}
