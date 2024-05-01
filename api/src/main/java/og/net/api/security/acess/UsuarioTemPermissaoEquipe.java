package og.net.api.security.acess;

import lombok.AllArgsConstructor;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.model.entity.*;
import og.net.api.service.EquipeService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class UsuarioTemPermissaoEquipe implements AuthorizationManager<RequestAuthorizationContext> {
    private EquipeService equipeService;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Map<String, String> variables = object.getVariables();
        String request = object.getRequest().getMethod();
        Equipe equipe;
        Integer equipeId = Integer.parseInt(variables.get("equipeId"));
        boolean autorizado = false;
        try {
            equipe = equipeService.buscarUm(equipeId);
        } catch (EquipeNaoEncontradaException e) {
            throw new RuntimeException(e);
        }
        UsuarioDetailsEntity usuarioDetailsEntity = (UsuarioDetailsEntity) authentication.get().getPrincipal();

        return new AuthorizationDecision(contemAutorizacao(usuarioDetailsEntity.getUsuario(),request,equipe));
    }

    private EquipeUsuario usuarioPertenceEquipe(Usuario usuario, Equipe equipe){
        return (EquipeUsuario) usuario.getEquipes().stream().filter(equipeUsuario -> equipeUsuario.getEquipe().equals(equipe));
    }

    private boolean contemAutorizacao (Usuario usuario, String request, Equipe equipe){
        if(usuarioPertenceEquipe(usuario,equipe) !=null) {
            if(usuarioPertenceEquipe(usuario,equipe).getCriador()){
                return true;
            }
            for (Permissao permissao : usuarioPertenceEquipe(usuario, equipe).getPermissao()) {
                if (permissao.getAuthority().equals(request)) {
                    return true;
                }
            }
        }
        return false;
    }
}
