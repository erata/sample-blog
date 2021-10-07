package blog.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String username;
    private String email;
    private String image;
    private String aboutUser;
    private Boolean isActive;
}