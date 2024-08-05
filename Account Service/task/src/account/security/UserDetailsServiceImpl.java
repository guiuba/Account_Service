package account.security;

import account.dao.UserRepo;
import account.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service//("userDetailsService")
//@Transactional
//@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findUserByEmailIgnoreCase(username);//findUserByUsername(username)

       /* if (!user.isPresent()) {
            throw new UsernameNotFoundException("Not found: " + username);
        }


        UserDetails userDetails =  new UserDetailsImpl(user.get());

        return userDetails;*/

        return userRepo
                .findUserByEmailIgnoreCase(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username \"" + username + "\" not found"));

    }
/*    private Collection<GrantedAuthority> getAuthorities(User user){
        Set<Group> userGroups = user.getUserGroups();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userGroups.size());
        for(Group userGroup : userGroups){
            authorities.add(new SimpleGrantedAuthority(userGroup.getName().toUpperCase()));
        }

        return authorities;
    }*/
}
