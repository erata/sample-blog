package blog.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostUpdateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    private String title;

    private String content;

    private List<String> categories = new ArrayList<>();

    private Boolean isPublished;
}