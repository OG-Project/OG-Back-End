package og.net.api.webSocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

@Controller
public class WebSocketControllerProjeto extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        int equipeIdDaSessao = obterIdEquipeDaSessao(session);
        int equipeIdDaMensagem = obterIdEquipeDaMensagem(message);
        if (equipeIdDaSessao == equipeIdDaMensagem) {
            session.sendMessage(message);
        }
    }

    private int obterIdEquipeDaSessao(WebSocketSession session) {

        String uri = session.getUri().toString();
        String[] parts = uri.split("/");
        return Integer.parseInt(parts[parts.length - 1]);
    }

    private int obterIdEquipeDaMensagem(TextMessage message) {
        String [] equipeIdDaMensagem = message.getPayload().split(":");
        return Integer.parseInt(equipeIdDaMensagem[equipeIdDaMensagem.length-1]); // Extrai o ID da equipe da mensagem
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
