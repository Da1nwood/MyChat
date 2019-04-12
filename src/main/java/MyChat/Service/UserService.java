package MyChat.Service;

import MyChat.Model.Roles;
import MyChat.Model.Users;
import MyChat.rep.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("no such user"));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Roles role: user.getAuthorities()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        }


        return new User(user.getUsername(), user.getPassword(), user.isEnabled()
                ,user.isAccountNonExpired()
                ,user.isCredentialsNonExpired()
                ,user.isAccountNonLocked()
                ,grantedAuthorities);
    }
    @Transactional(readOnly = true)
    Optional<Users> userIsExists(String username){
        return userRepository.findByUsername(username);
    }
    @Transactional(readOnly = true)
    boolean emailIsExist(String email){
        return userRepository.findUsersByEmail(email).isPresent();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> addNewUser(Users user) throws Exception {

        if(userIsExists(user.getUsername()).isPresent() || emailIsExist(user.getEmail())){
            return new ResponseEntity<String>("User or Email is Exist,", HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Collections.singleton(Roles.ROLE_USER));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setActivationCode(String.valueOf(100000000 + (long) (Math.random() *999999999 )));
        user.setEnabled(false);
        userRepository.save(user);
        sendMessage(user);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
    public boolean activateUser(String code) {
        Optional<Users> user = userRepository.findByActivationCode(code);

        if (user.isPresent()) {
            user.get().setEnabled(true);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }
    private void sendMessage(Users user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            " Please, visit next link: https://hakaton2019.herokuapp.com/login/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> savePhoto(@NotNull String photo,String name){
        if(userIsExists(name).isPresent() ){
            Users users = userRepository.findByUsername(name).get();
            users.setPhoto(photo);
            System.out.println(photo);
            userRepository.save(users);
            return new ResponseEntity<String>(HttpStatus.CREATED);
        }
        else return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> getPhoto(Optional<String> name){
        if(userIsExists(name.get()).isPresent() ){
            Users users = userRepository.findByUsername(name.get()).get();
            return new ResponseEntity(users.getPhoto(),HttpStatus.OK);
        }
        else return new ResponseEntity ("",HttpStatus.OK);
    }
}
