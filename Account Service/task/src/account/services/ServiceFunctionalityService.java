package account.services;

import account.customExceptions.CustomErrorMessage;
import account.customExceptions.UserNotFoundException;
import account.dao.UserRepo;
import account.dto.UserDto;
import account.enums.UserGroup;
import account.enums.UserRole;
import account.mapper.Mapper;
import account.model.RoleHandler;
import account.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Component
public class ServiceFunctionalityService {
    UserRepo userRepo;
    Mapper mapper;

    @Autowired
    public ServiceFunctionalityService(UserRepo userRepo, Mapper mapper) {
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    public ResponseEntity<?> changeUserRole(
            Exception ex, WebRequest request, UserDetails userDetails, RoleHandler roleHandler) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        Optional<User> loggedUser = userRepo.findUserByEmailIgnoreCase(userDetails.getUsername());
        Optional<User> user = userRepo.findUserByEmailIgnoreCase(roleHandler.getUser());
        if (!user.isPresent()) {
            return getCustomResponseEntity(
                    UserNotFoundException.message,
                    HttpStatus.NOT_FOUND,
                    path);
            //   throw new UserNotFoundException();
        } else if (!UserRole.rolesList().contains(roleHandler.getRole())) { //.  .stream(UserRole.values().).findAny(roleHandler.getRole())
            return getCustomResponseEntity(
                    "Role not found!",
                    HttpStatus.NOT_FOUND,
                    path);

        } else {
            String operation = roleHandler.getOperation();
            switch (operation) {

                case "GRANT":
                    if (user.get().getRoles().contains(UserRole.ADMINISTRATOR) && //
                            !UserGroup.isAdministrative(UserRole.valueOf(roleHandler.getRole())) ||
                            (user.get().getRoles().contains(UserRole.USER) || user.get().getRoles().contains(UserRole.ACCOUNTANT)) &&
                                    UserGroup.isAdministrative(UserRole.valueOf(roleHandler.getRole()))
                    ) {//user.get().getRoles()
                        return getCustomResponseEntity(
                                "The user cannot combine administrative and business roles!",
                                HttpStatus.BAD_REQUEST,
                                path);

                    } else {
                        user.get().getRoles().add(UserRole.valueOf(roleHandler.getRole()));
                        userRepo.save(user.get());
                        UserDto userDto = new UserDto(user.get()); //savedUser  user
                        return ResponseEntity.ok(userDto);

                    }
                 //   roleHandler.getRole();
               //     break;
                case "REMOVE":
                    if ("ADMINISTRATOR".equals(roleHandler.getRole())) {
                        return getCustomResponseEntity(
                                "Can't remove ADMINISTRATOR role!",
                                HttpStatus.BAD_REQUEST,
                                path);
                    } else if (user.get().getRoles().size() == 1) {
                        return getCustomResponseEntity(
                                "The user must have at least one role!",
                                HttpStatus.BAD_REQUEST,
                                path);
                    } else if (!user.get().getRoles().contains(UserRole.valueOf(roleHandler.getRole()))) {
                        return getCustomResponseEntity(
                                "The user does not have a role!",
                                HttpStatus.BAD_REQUEST,
                                path);
                    } else {
                        //remove role
                        user.get().getRoles().remove(UserRole.valueOf(roleHandler.getRole()));
                        userRepo.save(user.get());
                        UserDto userDto = new UserDto(user.get()); //savedUser  user
                        return ResponseEntity.ok(userDto);
                    }

                default:
            }
          /*  return getCustomResponseEntity(
                    "Role not found!!",
                    HttpStatus.NOT_FOUND,
                    path);*/

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteUser(String userEmail, WebRequest request, UserDetails userDetails) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        Optional<User> userToDelete = userRepo.findUserByEmailIgnoreCase(userEmail);
        Optional<User> userLogged = userRepo.findUserByEmailIgnoreCase(userDetails.getUsername());
        if (!userToDelete.isPresent()) {
            return getCustomResponseEntity(
                    UserNotFoundException.message,
                    HttpStatus.NOT_FOUND,
                    path);
        } /*else if (!userLogged.get().getRoles().contains(UserRole.ADMINISTRATOR)) {
            return getCustomResponseEntity(
                    "Access Denied!",
                    HttpStatus.FORBIDDEN,
                    path);

        }*/ else if (userToDelete.get().roles.contains(UserRole.ADMINISTRATOR)) {
            return getCustomResponseEntity(
                    "Can't remove ADMINISTRATOR role!",
                    HttpStatus.BAD_REQUEST,
                    path);
        } else {
            userRepo.delete(userToDelete.get());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            "email", userToDelete.get().getEmail().toLowerCase(),
                            "status", "Deleted successfully!"));
        }

    }

    public List<UserDto> getAllUsers() { //String
        //   List<User> userList = (List<User>) userRepo.findAll();
        List<User> userList = userRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
       /*

         return userList.stream().map(UserResponse::new).collect(Collectors.toList());
        */
        return userList.stream().map(UserDto::new).toList(); //collect(Collectors.toList())
  /*      List<UserDto> userDtoList = new ArrayList<>();
        userList.forEach(user -> userDtoList.add(mapper.toUserDto(user)));

        return  userDtoList;*/


        //  return userList;

        //return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    ResponseEntity<?> getCustomResponseEntity(String message, HttpStatus httpStatus, String path) {
        return new ResponseEntity<>(new CustomErrorMessage(
                LocalDate.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message,
                path),
                httpStatus);
        //       HttpStatus.BAD_REQUEST);
    }


}
