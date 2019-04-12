package MyChat.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserContoller {
    @GetMapping
    public ResponseEntity kek(){
        return new ResponseEntity("Hello", HttpStatus.OK);
    }
}
