package MyChat.rep;

import MyChat.Model.Publics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicsRep extends JpaRepository<Publics,Long>{
    Optional<Publics> findByTopicName(String nameTopic);

}
