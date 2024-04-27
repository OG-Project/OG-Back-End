package og.net.api.security.acess;

import lombok.AllArgsConstructor;
import og.net.api.model.entity.Usuario;
import og.net.api.model.entity.UsuarioDetailsEntity;
import og.net.api.security.utils.ContemAutorizacaoUtil;
import og.net.api.service.UsuarioService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class IsUser implements AuthorizationManager<RequestAuthorizationContext> {
    private ContemAutorizacaoUtil utils;
    private UsuarioService usuarioService;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Map<String, String> variables = object.getVariables();

        String request = object.getRequest().getMethod();

        Integer userID = Integer.parseInt(variables.get("id"));

        UsuarioDetailsEntity usuarioDetailsEntity = (UsuarioDetailsEntity) authentication.get().getPrincipal();

        Usuario usuarioFezRequest = usuarioDetailsEntity.getUsuario();

        Usuario usuarioConfiguracao = usuarioService.buscarUm(userID);

        boolean autorizado= false;

        if(Objects.equals(usuarioFezRequest.getId(), userID)
                && utils.contemAutorizacao(request,usuarioDetailsEntity)){
            autorizado = true;
        }else if(usuarioConfiguracao.getConfiguracao().getIsVisualizaPerfil() && request.equals("GET")){
            autorizado = true;
        }

        return new AuthorizationDecision(autorizado);
    }
}
