package MyChat.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "publics")
public class Publics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String idGroup;

    private String topicName;


        @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "publics")
    private Set<Users> users;

}
