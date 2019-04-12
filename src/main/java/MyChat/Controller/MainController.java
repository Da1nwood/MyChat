package MyChat.Controller;

import MyChat.Model.Users;
import MyChat.Service.SecurityService;
import MyChat.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class MainController {
    @Autowired
    SecurityService securityService;
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody Users user, HttpServletRequest httpRequest) {
        ResponseEntity<String> responseEntity = securityService.autorization(user.getUsername(), user.getPassword());
        httpRequest.getSession().setAttribute("Name", user.getUsername());
        return responseEntity;
}
    @GetMapping("/getName")
    public ResponseEntity<String> getNameBySession(HttpServletRequest httpRequest) {
        try {
            Optional<String> name = Optional.of((String) httpRequest.getSession().getAttribute("Name"));
            return new ResponseEntity<>(name.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpRequest){
        SecurityContextHolder.getContext().setAuthentication(null);
        httpRequest.getSession().invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/activate/{code}")
    public ResponseEntity<String> activateUser(@PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if(isActivated){
            return new ResponseEntity<>("Autorize",HttpStatus.OK);
        }
        else return new ResponseEntity<>("No Autorize",HttpStatus.UNAUTHORIZED);
    }
}
