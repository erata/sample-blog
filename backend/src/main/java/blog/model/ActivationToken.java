package blog.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
@Entity
public class ActivationToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="activation_token")
    private String activationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public ActivationToken(User user) {
        this.user = user;
        createdDate = new Date();
        activationToken = UUID.randomUUID().toString();
    }
}