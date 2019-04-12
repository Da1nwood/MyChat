package MyChat.Controller;

import MyChat.Service.UserService;
import MyChat.dto.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/pictures")
public class PicturesController {
    @Autowired
    UserService userService;

    @PostMapping("/addPicture")
    public ResponseEntity<String> addPhoto(@RequestBody Photo photo, HttpServletRequest httpRequest) {
        Optional<String> name = Optional.of((String) httpRequest.getSession().getAttribute("Name"));
        return userService.savePhoto(photo.getPhoto(), name.get());
    }

    @PostMapping("/getPicture")
    public ResponseEntity<String> getPicture(@NotNull HttpServletRequest httpRequest) {
        Optional<String> name = Optional.of((String) httpRequest.getSession().getAttribute("Name"));
        return userService.getPhoto(name);
    }
}
