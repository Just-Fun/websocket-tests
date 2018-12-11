package com.serzh.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class WebSocketEventListener /*implements WebSocketHandler*/ {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;
    private static final String CLIENT_ID = "client-id";
//    private final WebSocketSessionStore webSocketSessionStore;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    /*SessionConnectedEvent — published shortly after a SessionConnectEvent when the broker has sent a STOMP CONNECTED frame in response to the CONNECT. At this point the STOMP session can be considered fully established.
SessionSubscribeEvent — published when a new STOMP SUBSCRIBE is received.
SessionUnsubscribeEvent — published when a new STOMP UNSUBSCRIBE is received.
SessionDisconnectEvent — published when a STOMP session ends. The DISCONNECT may have been sent from the client, or it may also be automatically generated when the WebSocket session is closed. In some cases this event may be published more than once per session. Components should be idempotent with regard to multiple disconnect events.*/


    @EventListener
    public void handleWebSocketConnectedListener(SessionConnectedEvent event) {

//        MessageHeaderAccessor
//        SimpMessageHeaderAccessor
//        StompHeaderAccessor

        StompHeaderAccessor stompAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = stompAccessor.getSessionId();

        Object header1 = stompAccessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);

        @SuppressWarnings("unchecked")
        String clientId4 = Optional.ofNullable(stompAccessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER))
                .map(o -> (GenericMessage) o)
                .map(GenericMessage::getHeaders)
                .map(messageHeaders -> messageHeaders.get(SimpMessageHeaderAccessor.NATIVE_HEADERS))
                .map(o -> (Map<String, List<String>>) o)
                .map(stringStringMap -> stringStringMap.get("clientId").get(0))
                .orElseThrow(() -> new RuntimeException("???"));

        System.out.println();

    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {

//        MessageHeaderAccessor
//        SimpMessageHeaderAccessor
//        StompHeaderAccessor

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        String clientId = headerAccessor.getFirstNativeHeader(CLIENT_ID);

        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        Objects.requireNonNull(sessionAttributes).put(CLIENT_ID, clientId);


        System.out.println();

    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();

//        String spaceKey = (String) webSocketSessionStore.getAttribute(sessionId, "spaceKey");


        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        String clientId = (String) Objects.requireNonNull(sessionAttributes).get("CLIENT_ID");
        if(clientId != null) {
            logger.info("User Disconnected : " + clientId);
        }



    }

/*    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println();
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println();
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println();
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }*/
}

/*        GenericMessage connectHeader = (GenericMessage) header1;    // FIXME find a way to pass the username to the server
        MessageHeaders headers1 = connectHeader.getHeaders();
        Object o = headers1.get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
        GenericMessage nativeHeaders2 = (GenericMessage) o;

        MessageHeaders headers2 = nativeHeaders2.getHeaders();
        String clientId3 = headers2.get("clientId", String.class);
        String clientId2 = (String) headers2.get("clientId");

//        Collections<UnmodifiableObservableMap<String, Object> check1 =o;
        @SuppressWarnings("unchecked")
        Map<String, Object> check2 = (Map<String, Object>) o;


        Map<String, List<String>> nativeHeaders25 = (Map<String, List<String>>) connectHeader.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

        String login = nativeHeaders25.get("clientId").get(0);
        String sessionId = stompAccessor.getSessionId();

        logger.info("Chat connection by user <{}> with sessionId <{}>", login, sessionId);




        MessageHeaders messageHeaders = stompAccessor.getMessageHeaders();
        Object header = stompAccessor.getHeader("simpConnectMessage");
        Object header2 = stompAccessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
//        GenericMessage<?> generic = (GenericMessage<?>) stompAccessor.getHeader("simpConnectMessage");
        GenericMessage<?> generic = (GenericMessage<?>) stompAccessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
//        GenericMessage<?> generic = (GenericMessage<?>) stompAccessor.getHeader("simpConnectMessage");
        MessageHeaders headers = generic.getHeaders();
        Object nativeHeaders55 = headers.get("nativeHeaders");

        System.out.println(nativeHeaders55);






        List<String> clientId = stompAccessor.getNativeHeader("clientId");
        String clientId1 = stompAccessor.getFirstNativeHeader("clientId");
        //login get from browser
        MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(event.getMessage(), SimpMessageHeaderAccessor.class);


        Map<String, Object> sessionAttributes = stompAccessor.getSessionAttributes();
//        sessionAttributes.get("username");

        MessageHeaders msgHeaders = event.getMessage().getHeaders();
//        Principal princ = (Principal) msgHeaders.get("simpUser");
        StompHeaderAccessor sha2 = StompHeaderAccessor.wrap(event.getMessage());
        List<String> nativeHeaders = sha2.getNativeHeader("clientId");


        StompHeaderAccessor accessor11 =
                MessageHeaderAccessor.getAccessor(event.getMessage(), StompHeaderAccessor.class);
        String firstNativeHeader = accessor11.getFirstNativeHeader("clientId");*/



        /*StompHeaderAccessor stompAccessor = StompHeaderAccessor.wrap(event.getMessage());


        GenericMessage connectHeader = (GenericMessage)stompAccessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);    // FIXME find a way to pass the username to the server
        Map<String, List<String>> nativeHeaders2 = (Map<String, List<String>>) connectHeader.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

        String login = nativeHeaders2.get("clientId").get(0);
        String sessionId = stompAccessor.getSessionId();

        logger.info("Chat connection by user <{}> with sessionId <{}>", login, sessionId);*/
