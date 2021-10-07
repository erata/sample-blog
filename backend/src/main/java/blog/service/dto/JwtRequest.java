package blog.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "username is mandatory")
    @Size(max = 50)
    private String username;

    @NotBlank(message = "password is mandatory")
    @Size(max = 128)
    private String password;
}
