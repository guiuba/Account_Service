package account.dao;

import account.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentsRepo extends CrudRepository<Payment, Long> {

     List<Payment> findAllByEmployee(String employee);
     Optional<Payment> findByEmployeeIgnoreCaseAndAndPeriod(String employee, String period);

}
