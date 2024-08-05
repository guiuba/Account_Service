package account.security;

import account.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter


public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

  /*      Set<Group> userGroups = user.getUserGroups();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userGroups.size());
        for (Group userGroup : userGroups) {

            authorities.add(new SimpleGrantedAuthority(userGroup.getName().toUpperCase())); //new SimpleGrantedAuthority("ROLE_USER")
        }
         return authorities;
        */

      /*  return user.getUserGroups()
                .stream()
                .map(group -> new SimpleGrantedAuthority(group.getName()))
                .collect(Collectors.toList());*/
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getUserRole()))
             //   .map(role -> new SimpleGrantedAuthority(role.getUserRole().name()))
                .collect(Collectors.toList());



    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }  // user.getName()

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //!"locked".equalsIgnoreCase(user.getOperation())

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
