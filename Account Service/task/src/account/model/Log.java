package account.model;

import account.enums.EventAction;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "log")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private Date date;

    @Enumerated(EnumType.STRING)
    private EventAction action;

    @Column
    private String subject;

    @Column
    private String object;

    @Column
    private String path;
}
