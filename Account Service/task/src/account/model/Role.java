package account.model;

import account.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
//@ToString(exclude = "userGroups")
@Table(name = "roles"/*, uniqueConstraints = {
        @UniqueConstraint(name = "UniqueUserRole", columnNames = {"name", "useRole"})} */
        )
public class Role {
    @Id
    @Column(name = "role_id") //   id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
  //  @JsonIgnore
    private long id;


    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}
