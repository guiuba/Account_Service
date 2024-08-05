package account.dao;

import account.model.Payroll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepo extends CrudRepository<Payroll, Long> {
    List<Payroll> findAllByEmailIgnoreCaseOrderBySalaryIdDesc(String email);
    Optional<Payroll> findByEmailIgnoreCaseAndSalaryId(String email, Long salaryId);

    Optional<Payroll> findByEmailIgnoreCaseAndPeriod(String email, String period);

}
