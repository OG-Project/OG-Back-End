package og.net.api.webSocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

public class WebSocketControllerUsuario extends AbstractWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        int equipeIdDaSessao = obterIdEquipeDaSessao(session);
//        int equipeIdDaMensagem = obterIdEquipeDaMensagem(message);
//        if (equipeIdDaSessao == equipeIdDaMensagem) {'
        System.out.println("É aqui");
            session.sendMessage(message);
//        }
    }

    private int obterIdUsuarioDaSessao(WebSocketSession session) {

        String uri = session.getUri().toString();
        String[] parts = uri.split("/");
        return Integer.parseInt(parts[parts.length - 1]);
    }

    private int obterIdUsuarioDaMensagem(TextMessage message) {
        String [] usuarioIdDaMensagem = message.getPayload().split(":");
        return Integer.parseInt(usuarioIdDaMensagem[usuarioIdDaMensagem.length-1]);
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        sessions.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
    }
}
