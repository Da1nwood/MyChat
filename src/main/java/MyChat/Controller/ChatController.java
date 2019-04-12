package MyChat.Controller;

import MyChat.Model.ChatMessage;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage/{fleetId}")
    @SendTo("/topic/public/{fleetId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String fleetId, SimpMessageHeaderAccessor headerAccessor) {
//        chatMessage.setPhoto((String) headerAccessor.getSessionAttributes().get("login"));
        return chatMessage;
    }

    @MessageMapping("/chat.addUser/{fleetId}")
    @SendTo("/topic/public/{fleetId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,@DestinationVariable String fleetId,SimpMessageHeaderAccessor headerAccessor) {
//        chatMessage.setPhoto((String) headerAccessor.getSessionAttributes().get("login"));
        return chatMessage;
    }

    @MessageMapping("/chat.sendMessageAndroid/{fleetId}")
    @SendTo("/topic/public/{fleetId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
//        chatMessage.setPhoto((String) headerAccessor.getSessionAttributes().get("login"));
//        ChatMessage chatMessage1 = new ChatMessage()
        return chatMessage;
    }
}
