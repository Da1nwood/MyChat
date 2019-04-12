package MyChat.dto;

import lombok.Data;

@Data
public class Sender {
    private String nameSeller;
    private String numberSocketSession;
    private String photo;
    private String message;

    public Sender(String nameSeller, String numberSocketSession, String photo, String message) {
        this.nameSeller = nameSeller;
        this.numberSocketSession = numberSocketSession;
        this.photo = photo;
        this.message = message;
    }
}
