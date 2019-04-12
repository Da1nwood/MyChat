package MyChat.Service;

import MyChat.Model.Publics;
import MyChat.rep.PublicsRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PublicService {
    @Autowired
    PublicsRep publicsRep;

    @Autowired
    UserService userService;

    private boolean groupIsExist(String nameTopic){
        return publicsRep.findByTopicName(nameTopic).isPresent();
    }
    public ResponseEntity<String> createNewGroup(String nameTopic){
        {
            Publics publics = new Publics();
            publics.setTopicName(nameTopic);
            String idGrouop = UUID.randomUUID().toString();
            publics.setIdGroup(idGrouop);
            return new ResponseEntity<String>(idGrouop, HttpStatus.CREATED);
        }
    }
}
