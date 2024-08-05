package account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payroll")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "period")
    private String period;

    @Column(name = "salary")
    private String salary;

    @Column(name = "email")
    @JsonIgnore
    private String email;

    @Column(name = "salary_id")
    @JsonIgnore
    private Long salaryId;

 /*   @Override
    public String toString() {
        String month = period.getMonth().toString().charAt(0) +
                period.getMonth().toString().substring(1).toLowerCase();

        //  payroll.setPeriod(String.format("%s-%s", month, period.getYear()));
        return "{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", period=" + String.format("%s-%s", month, period.getYear()) +
                ", salary='" + salary + '\'' +
                '}';
    }*/
}
