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
public class PostDetailsView implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String content;
    private Boolean isPublished;
    private Date createdAt;
    private Date updatedAt;
    private String username;
    private List<CommentDto> comments = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
}
