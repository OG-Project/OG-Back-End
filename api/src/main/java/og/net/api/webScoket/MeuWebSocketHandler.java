package og.net.api.webScoket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MeuWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String mensagemRecebida = (String) message.getPayload();
       if(requisicaoPost(mensagemRecebida)){
           session.sendMessage(new TextMessage("post: "+ mensagemRecebida));
       }else if (requisicaoPut(mensagemRecebida)){
           session.sendMessage(new TextMessage("put: "+ mensagemRecebida));
       }else if(requisicaoDelete(mensagemRecebida)){
           session.sendMessage(new TextMessage("Delete: "+ mensagemRecebida));
       }
        session.sendMessage(new TextMessage(mensagemRecebida));
    }

    private boolean requisicaoPost(String mensagemRecebida){
        return switch (mensagemRecebida) {
            case "post-notificacao-projeto" ->
                // cria uma notificacao de projeto
                    true;
            case "post-notificacao-tarefa" ->
                // cria uma notificacao de tarefa
                    true;
            case "post-notificacao-equipe" ->
                // cria uma notificacao de equipe
                    true;
            case "post-notificacao-convite" ->
                // cria uma notificacao de convite
                    true;
            default -> false;
        };
    }

    private boolean requisicaoPut(String mensagemRecebida){
        return switch (mensagemRecebida) {
            case "put-notificacao-projeto" ->
                // cria uma notificacao de projeto editado
                    true;
            case "put-notificacao-tarefa" ->
                // cria uma notificacao de tarefa editado
                    true;
            case "put-notificacao-equipe" ->
                // cria uma notificacao de equipe editado
                    true;
            case "put-notificacao-convite" ->
                // cria uma notificacao de convite editado
                    true;
            default -> false;
        };
    }

    private boolean requisicaoDelete(String mensagemRecebida){
        return switch (mensagemRecebida) {
            case "delete-notificacao-projeto" ->
                // cria uma notificacao de projeto deletado
                    true;
            case "delete-notificacao-tarefa" ->
                // cria uma notificacao de tarefa deletado
                    true;
            case "delete-notificacao-equipe" ->
                // cria uma notificacao de equipe deletado
                    true;
            case "delete-notificacao-convite" ->
                // cria uma notificacao de convite deletado
                    true;
            default -> false;
        };
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
