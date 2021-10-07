package blog.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class UserUpdateDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String username;

    @Size(max = 100)
    @Email
    private String email;

    @Size(max = 128)
    private String password;

    private String image;

    @Size(max = 255)
    private String aboutUser;

    private Boolean isActive;

}
