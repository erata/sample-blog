package blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import blog.exception.RecordAlreadyExistException;
import blog.exception.RecordNotFoundException;
import blog.model.ActivationToken;
import blog.model.User;
import blog.repository.ActivationTokenRepository;
import blog.repository.UserRepository;
import blog.security.JwtTokenUtil;
import blog.service.dto.UserDetailsDto;
import blog.service.dto.UserDto;
import blog.service.dto.JwtRequest;
import blog.service.dto.UserUpdateDto;
import blog.util.NullAwareBeanUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    final ActivationTokenRepository activationTokenRepository;
    final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder bcryptEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenUtil jwtTokenUtil,
                       JwtUserDetailsService userDetailsService,
                       ActivationTokenRepository activationTokenRepository,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.activationTokenRepository = activationTokenRepository;
        this.emailService = emailService;
    }

    public UserDetailsDto createUser(UserDto userDto)
    {
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if(userOptional.isPresent()) {
            throw new RecordAlreadyExistException("User already exist for username = " + userDto.getUsername());
        }

        User user =  new User();
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(bcryptEncoder.encode(userDto.getPassword()));

        user = userRepository.save(user);

        UserDetailsDto userDetailsDto = new UserDetailsDto();
        BeanUtils.copyProperties(user, userDetailsDto);

        sendConfirmationMessage(user);

        return userDetailsDto;
    }

    private void sendConfirmationMessage(User user) {
        ActivationToken activationToken = new ActivationToken(user);
        activationTokenRepository.save(activationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click below the link : \n"
                +"http://localhost:8080/users/activate?token="+ activationToken.getActivationToken());

        emailService.sendEmail(mailMessage);
    }

    public String createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        return jwtTokenUtil.generateToken(userDetails);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public List<UserDetailsDto> getAllUsers()
    {
        List<User> userList = userRepository.findAll();

        if(userList.size() > 0) {

            List<UserDetailsDto> userDetailsDtoList = new ArrayList<>();
            userList.forEach(user -> {
                UserDetailsDto userDetailsDto = new UserDetailsDto();
                BeanUtils.copyProperties(user, userDetailsDto);
                userDetailsDtoList.add(userDetailsDto);
            });

            return userDetailsDtoList;
        } else {
            return new ArrayList<UserDetailsDto>();
        }
    }

    public UserDetailsDto getUserById(Integer id) throws RecordNotFoundException
    {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            BeanUtils.copyProperties(user.get(), userDetailsDto);

            return userDetailsDto;
        } else {
            throw new RecordNotFoundException("No user record exist for id = " + id);
        }
    }

    public UserDetailsDto getUserByUsername(String username) throws RecordNotFoundException
    {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            BeanUtils.copyProperties(user.get(), userDetailsDto);

            return userDetailsDto;
        } else {
            throw new RecordNotFoundException("No user record exist for username = " + username);
        }
    }

    public void activateUser(String username) throws RecordNotFoundException
    {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(true);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("No user record exist for username = " + username);
        }
    }
    
   public UserDetailsDto updateUser(Integer id, UserUpdateDto userDto) throws RecordNotFoundException
    {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent())
        {
            User user = userOptional.get();
            NullAwareBeanUtilsBean.copyNonNullProperties(userDto, user);
            //user.setName(userDto.getName());
            //user.setEmail(userDto.getEmail());
            if(userDto.getPassword() != null) {
                user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
            }

            user = userRepository.save(user);

            UserDetailsDto userDetailsDto = new UserDetailsDto();
            BeanUtils.copyProperties(user, userDetailsDto);

            return userDetailsDto;

        } else {

            throw new RecordNotFoundException("No user record exist for id = " + id);
        }
    }


    public void deleteUserById(Integer id) throws RecordNotFoundException
    {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent())
        {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user record exist for id = " + id);
        }
    }

}