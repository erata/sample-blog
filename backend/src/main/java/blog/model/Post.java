package blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"user", "comments", "categories"})
@EqualsAndHashCode(exclude = {"user", "comments", "categories"})
@Entity
@Table(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    @NotBlank(message = "title is mandatory")
    @Size(max = 100)
    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @NotBlank(message = "content is mandatory")
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

    public void removeAllComments() {
        for (Comment comment : new ArrayList<>(comments)) {
            removeComment(comment);
        }
    }

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(name = "post_category",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category) {
        categories.add(category);
        category.getPosts().add(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getPosts().remove(this);
    }

    public void removeAllCategories() {
        for (Category category : new ArrayList<>(categories)) {
            removeCategory(category);
        }
    }

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
