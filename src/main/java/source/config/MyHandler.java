package source.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class MyHandler implements  WebSocketHandler {

	private static Collection<WebSocketSession> setWebSocketSession = Collections.synchronizedCollection(new HashSet<WebSocketSession>());
	
	
	@Override
	public void  afterConnectionEstablished(WebSocketSession session) throws Exception {
		setWebSocketSession.add(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		sendToAll(new TextMessage((CharSequence) message.getPayload()));
	}

	private void sendToAll(TextMessage tm) throws IOException {
		for (WebSocketSession currSession : setWebSocketSession) {
			currSession.sendMessage(tm);
	}
	}
	
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		setWebSocketSession.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
