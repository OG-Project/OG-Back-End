package og.net.api.webScoket;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;



@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketControllerEquipe(), "/og/webSocket/equipe/{id}").setAllowedOrigins("*");
        registry.addHandler(new WebSocketControllerUsuario(), "/og/webSocket/usuario/{id}").setAllowedOrigins("*");

    }
}
