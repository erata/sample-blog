package blog.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostListItemView implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean isPublished;
    private Date createdAt;
    private String userName;
    private Integer commentCount;
    private List<String> categories = new ArrayList<>();
}