package MyChat.Controller;

import MyChat.Service.PublicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/Groups")
public class PublicsController {
    @Autowired
    private PublicService publicService;

    @PostMapping("/addGroup")
    public ResponseEntity<String> addGroup(@NotNull String name){
        return publicService.createNewGroup(name);
    }
}
