package blog.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "posts")
@EqualsAndHashCode(exclude = "posts")
@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Integer id;

    @NotBlank(message = "name is mandatory")
    @Size(max = 50)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "categories")
    @Setter(AccessLevel.PRIVATE)
    private Set<Post> posts = new HashSet<>();

    public Category(String name) {
        this.name = name;
    }
}