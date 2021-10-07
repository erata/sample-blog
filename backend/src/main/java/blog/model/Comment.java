package blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "post")
@EqualsAndHashCode(exclude = "post")
@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.PRIVATE)
    private Integer id;

    @NotBlank(message = "name is mandatory")
    @Size(max = 50)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "email is mandatory")
    @Size(max = 100)
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "content is mandatory")
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @Setter(AccessLevel.PRIVATE)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(String name, String email, String content) {
        this.name = name;
        this.email = email;
        this.content = content;
    }
}