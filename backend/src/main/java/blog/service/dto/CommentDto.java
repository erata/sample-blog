package blog.service.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
public class CommentDto{
    @NotBlank(message = "name is mandatory")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "email is mandatory")
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank(message = "content is mandatory")
    private String content;
}