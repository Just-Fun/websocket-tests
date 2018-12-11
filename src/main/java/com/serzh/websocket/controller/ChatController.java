package com.serzh.websocket.controller;

import com.serzh.websocket.model.ChatTestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ChatController {

    private AtomicInteger counter = new AtomicInteger(1);

    private final SimpMessagingTemplate template;

    @Autowired
    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/message")
//    @SendTo("/topic/chat")
//    @SendToUser("/queue/reply")
//    public ChatTestMessage chat(ChatTestMessage message) throws Exception {
    public void chat(ChatTestMessage message/*, SimpMessageHeaderAccessor headerAccessor*/) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        headerAccessor.getSessionAttributes().put("usernameId", message.getMessage());

        template.convertAndSend("/topic/chat/"+ message.getMessage(),
                new ChatTestMessage(counter.getAndIncrement() + " fake message from User " + message.getMessage()));
//        return new ChatTestMessage(counter.getAndIncrement() + " fake message from User");
    }
/*    @MessageMapping("/message1")
    @SendTo("/topic/chat")
    public void sendMessages(ChatTestMessage message, Principal principal) throws Exception {
//        Thread.sleep(1000); // simulated delay
        template
                .convertAndSendToUser(principal.getName(), "/topic/chat",
                        new ChatTestMessage(counter.getAndIncrement() + " fake message from User"));
    }*/





}







