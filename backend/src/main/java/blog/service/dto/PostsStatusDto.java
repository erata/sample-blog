package blog.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostsStatusDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private List<String> ids;

    @NotNull
    private Boolean isPublished;
}