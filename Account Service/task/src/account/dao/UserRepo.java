package account.dao;

import account.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> { //CrudRepository

    Optional<User> findUserByEmailIgnoreCase(String email);
  //  User findUserByEmailIgnoreCase(String email);
    boolean existsUserByEmailIgnoreCase(String email);
 //   Optional<User> findUserByUsername(String username);

    boolean existsUserByIdEquals(long id);

    List<User> findAll(Sort sort);

}
