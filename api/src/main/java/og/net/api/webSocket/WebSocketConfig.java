package og.net.api.webSocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;



@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketControllerEquipe(), "/og/webSocket/equipe/{id}").setAllowedOrigins("*");
        registry.addHandler(new WebSocketControllerUsuario(), "/og/webSocket/usuario/{id}").setAllowedOrigins("*");
        registry.addHandler(new WebSocketControllerProjeto(), "/og/webSocket/projeto/{id}").setAllowedOrigins("*");
        registry.addHandler(new WebSocketControllerNotificacao(), "/og/webSocket/convite/{id}").setAllowedOrigins("*");
        registry.addHandler(new WebSocketControllerNotificacao(), "/og/webSocket/notificacao/{id}").setAllowedOrigins("*");
    }
}
