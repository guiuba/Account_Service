package account.services;

import account.customExceptions.*;
import account.dao.UserRepo;
import account.dto.UserDto;
import account.enums.UserRole;
import account.mapper.Mapper;
import account.model.PasswordHandler;
import account.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepo userRepo;
   // private final GroupRepository groupRepo;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;


 //   private final EntityService entityService;

    private UserExistException userExistException;

    @Autowired
    public AuthenticationService(UserRepo userRepo, /*GroupRepository groupRepo,*/ Mapper mapper, PasswordEncoder passwordEncoder, /*EntityService entityService,*/ UserExistException userExistException) {
        this.userRepo = userRepo;
       // this.groupRepo = groupRepo;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
  //      this.entityService = entityService;
        this.userExistException = userExistException;
    }


    public ResponseEntity<?> signup(User user, Exception ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        Optional<User> userToAdd = userRepo.findUserByEmailIgnoreCase(user.getEmail()); //
        if (!userToAdd.isPresent()) {
            if (passwordIsShort(user.getPassword()))
                //   throw new ShortPasswordException();
                return getCustomResponseEntity(
                        ShortPasswordException.message,
                        path);

            if (isPasswordBreached(user.getPassword()))
                //  throw new BreachedPasswordException();

                return getCustomResponseEntity(
                        BreachedPasswordException.message,
                        path);


            user.setPassword(passwordEncoder.encode(user.getPassword())); //.toLowerCase()
          //  Group group; // = new Group(); //  Group group;
          //  Role role = new Role();
            if (!userRepo.existsUserByIdEquals(1L)) {
                user.getRoles().add(UserRole.ADMINISTRATOR);
            //    group = groupRepo.findByName("ROLE_ADMINISTRATOR");
           //     role.setUserRole(UserRole.ADMINISTRATOR);
              //  user.getRoles().add(new Role(user, UserRole.ADMINISTRATOR.name()));

            } else {
                user.getRoles().add(UserRole.USER);
             //   group = groupRepo.findByName("ROLE_USER");
       //         role.setUserRole(UserRole.USER);
            }
       //     role.setUser(user);
       //     user.setRoles(role.);
          //  user.getRoles().add(role);
            userRepo.save(user);
         //   User newUser = new User(user, passwordEncoder.encode(user.getPassword()));

            //   user.getUserGroups().add(group);
           // group.getUsers().add(user); // esse tava dando erro

         //   user.addGroup(group);

            //  group.setUsers(Set.of(user));
           // User savedUser = userRepo.save(newUser); // test
            //   groupRepo.save(group);

            //   entityService.insertEntities(user); // volta cod 401
            //   entityService.insertEntities(group);  // volta cod 401

            // user.getUserGroups().add(group);//addUserGroups(group);
            //   UserDto userDto = mapper.toUserDto(user);
            UserDto userDto = new UserDto(user); //savedUser  user
            return ResponseEntity.ok(userDto);


        } else {
            //  throw new UserExistException();
            return getCustomResponseEntity(
                    UserExistException.message,
                    path);
        }
    }

    public ResponseEntity<?> changePass(UserDetails userDetails,
                                        PasswordHandler passwordHandler,
                                        Exception ex, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        Optional<User> user = userRepo.findUserByEmailIgnoreCase(userDetails.getUsername());

        if (passwordEncoder.matches(passwordHandler.getNew_password(), user.get().getPassword())) {
            //    throw new SamePasswordException();
            return getCustomResponseEntity(
                    SamePasswordException.message,
                    path);
        } else {
            if (passwordIsShort(passwordHandler.getNew_password()))
                //      throw new ShortPasswordException();
                return getCustomResponseEntity(
                        ShortPasswordException.message,
                        path);

            if (isPasswordBreached(passwordHandler.getNew_password()))
                //    throw new BreachedPasswordException();
                return getCustomResponseEntity(
                        BreachedPasswordException.message,
                        path);
            else {
                user.get().setPassword(passwordEncoder.encode(passwordHandler.getNew_password()));
                userRepo.save(user.get());

                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Map.of(
                                "email", user.get().getEmail().toLowerCase(),
                                "status", "The password has been updated successfully"));

            }
        }
    }

    ResponseEntity<?> getCustomResponseEntity(String message, String path) {
        return new ResponseEntity<>(new CustomErrorMessage(
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                path),
                HttpStatus.BAD_REQUEST);
    }

    boolean passwordIsShort(String password) {
        return password.length() < 12;
    }

    boolean isPasswordBreached(String password) {
        List<String> breachedPasswords = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
                "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");
        return breachedPasswords.contains(password);
    }

}
