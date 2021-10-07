package blog.controller;

import blog.BlogApplication;
import blog.exception.RecordAlreadyExistException;
import blog.model.ActivationToken;
import blog.repository.ActivationTokenRepository;
import blog.exception.RecordNotFoundException;
import blog.service.UserService;
import blog.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(BlogApplication.class);
    private final UserService userService;
    final ActivationTokenRepository activationTokenRepository;

    @Autowired
    public UserController(UserService userService, ActivationTokenRepository activationTokenRepository) {
        this.userService = userService;
        this.activationTokenRepository = activationTokenRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDetailsDto> saveUser(@RequestBody UserDto user)
            throws RecordAlreadyExistException {
        UserDetailsDto userDetails = userService.createUser(user);
        logger.debug("saveUser(): " + userDetails.getId() + " :" + userDetails.getEmail());

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        UserDetailsDto user = userService.getUserByUsername(authenticationRequest.getUsername());

        if(user.getIsActive()) {

            String token = userService.createAuthenticationToken(authenticationRequest);
            logger.debug("Created Token: " + token);

            //UserDetailsDto user = userService.getUserByUsername(authenticationRequest.getUsername());
            Map<String, String> userMap = new HashMap<>();
            userMap.put("id", String.valueOf(user.getId()));
            userMap.put("name", user.getName());
            userMap.put("username", user.getUsername());
            userMap.put("email", user.getEmail());
            userMap.put("image", user.getImage());

            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("user", userMap);

            return ResponseEntity.ok(map);

        } else {
            throw new Exception("User account is not activated yet!");
        }
    }

    @GetMapping(value="/activate", produces = MediaType.TEXT_HTML_VALUE)
    public String aktivateUserAccount(@RequestParam("token") String activationToken)
    {
        ActivationToken token = activationTokenRepository.findByActivationToken(activationToken);

        if(token != null)
        {
            userService.activateUser(token.getUser().getUsername());
            return "<html>" +
                    "<body style='text-align: center'>" +
                    "<br><br>" +
                    "<h2>Congratulations!</h2>" +
                    "<h1>Account verified!</h1>" +
                    "<a href='http://localhost:4200/posts'>Return to Blog</a>" +
                    "</body>" +
                    "</html>";
        }
        else
        {
            return "<html>" +
                    "<body style='text-align: center'>" +
                    "<br><br>" +
                    "<h1>The link is invalid or broken!</h1>" +
                    "<a href='http://localhost:4200/posts'>Return to Blog</a>" +
                    "</body>" +
                    "</html>";
        }
    }

    @GetMapping()
    public ResponseEntity<List<UserDetailsDto>> getAllUsers() {
        List<UserDetailsDto> list = userService.getAllUsers();
        logger.debug("getAllUsers(): Found " + list.size() + " users");

        return new  ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable("id") Integer id)
            throws RecordNotFoundException {

        UserDetailsDto user = userService.getUserById(id);
        logger.debug("getUserById(): " + user.getId() + " :" + user.getEmail());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

   /* @GetMapping("/{username}")
    public ResponseEntity<UserDetailsDto> getUserByUsername(@PathVariable("username") String username)
            throws RecordNotFoundException {

        UserDetailsDto user = userService.getUserByUsername(username);
        logger.debug("getUserByUsername(): " + user.getId() + " :" + user.getEmail());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable("id") Integer id,
                                                     @RequestBody @Valid UserUpdateDto userDto)
            throws RecordNotFoundException {
        UserDetailsDto user = userService.updateUser(id, userDto);
        logger.debug("updateUser(): " + user.getId() + " :" + user.getEmail());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUserById(@PathVariable("id") Integer id)
            throws RecordNotFoundException {
        userService.deleteUserById(id);
        logger.debug("deleteUserById(): User removed for userId=" + id);

        return HttpStatus.FORBIDDEN;
    }
}