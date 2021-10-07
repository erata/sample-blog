package blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {/*"posts",*/ "password"})
@EqualsAndHashCode //(exclude = {"posts"})
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "name is mandatory")
    @Size(max = 50)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "username is mandatory")
    @Size(max = 50)
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank(message = "email is mandatory")
    @Size(max = 100)
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "password is mandatory")
    @Size(max = 128)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "image")
    private String image;

    @Size(max = 255)
    @Column(name = "about_user")
    private String aboutUser;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "is_active")
    private Boolean isActive = false;
 /*
   @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Post> posts;

    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setUser(null);
    }

    public void removeAllPosts() {
        for(Post post : new ArrayList<>(posts)) {
            removePost(post);
        }
    }*/

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}