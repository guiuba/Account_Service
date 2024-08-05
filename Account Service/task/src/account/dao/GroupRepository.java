package account.dao;

import account.model.Group;
import org.springframework.data.repository.CrudRepository;

//@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
  //  Group findByCode(String code); // customer
    Group findByName(String name);
}
