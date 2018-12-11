package com.serzh.websocket.config;

import com.serzh.websocket.model.ChatTestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

//@EnableScheduling
//@Configuration
public class SchedulerConfig {

    private AtomicInteger counter = new AtomicInteger(1);


    private final SimpMessagingTemplate template;

    @Autowired
    public SchedulerConfig(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedDelay = 30000)
    public void sendTestMessages() {
        template.convertAndSend("/topic/chat",
                new ChatTestMessage("Test message from User every 30 seconds"));
    }

}
