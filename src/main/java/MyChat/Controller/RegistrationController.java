package MyChat.Controller;


import MyChat.Model.Users;
import MyChat.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid Users user) throws Exception {
        return userService.addNewUser(user);
    }
}
