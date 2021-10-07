package blog.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "name is mandatory")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "username is mandatory")
    @Size(max = 50)
    private String username;

    @NotBlank(message = "email is mandatory")
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank(message = "password is mandatory")
    @Size(max = 128)
    private String password;

}