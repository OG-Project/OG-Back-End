package og.net.api.security.acess;

import lombok.AllArgsConstructor;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.model.entity.*;
import og.net.api.service.EquipeService;
import og.net.api.service.UsuarioService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class UsuarioTemPermissaoEquipe implements AuthorizationManager<RequestAuthorizationContext> {
    private EquipeService equipeService;
    private  final UsuarioService usuarioService;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Map<String, String> variables = object.getVariables();
        String request = object.getRequest().getMethod();
        Equipe equipe;
        Integer equipeId = Integer.parseInt(variables.get("equipeId"));
        Integer userId =0;
        Integer permissaoId =0;

        if(variables.get("usuarioId") != null){
            userId = Integer.parseInt(variables.get("usuarioId"));
        }
        if (variables.get("permissaoId") != null){
            permissaoId = Integer.parseInt(variables.get("permissaoId"));
        }

        boolean autorizado = false;
        try {
            equipe = equipeService.buscarUm(equipeId);
        } catch (EquipeNaoEncontradaException e) {
            throw new RuntimeException(e);
        }


        UsuarioDetailsEntity usuarioDetailsEntity = (UsuarioDetailsEntity) authentication.get().getPrincipal();
        Optional<Usuario> usuario = usuarioService.buscarUsuariosUsername(usuarioDetailsEntity.getUsername());

        if(object.getRequest().getRequestURI().equals("/usuario/add/"+userId+"/"+equipeId+"/"+permissaoId)){
            if(!verificaSeEquipeTemUsuario(equipe, usuario.get().getEquipes())){
               return new AuthorizationDecision(usuario.get().getUsuarioDetailsEntity().getAuthorities().contains(Permissao.PATCH));
            }
        }

        return new AuthorizationDecision(contemAutorizacao(usuario.get().getEquipes(),request,equipe));
    }

    private EquipeUsuario usuarioPertenceEquipe(List<EquipeUsuario> equipeUsuarios, Equipe equipe){
        for (EquipeUsuario equipeUsuario : equipeUsuarios){
            if(equipeUsuario.getEquipe().equals(equipe)){
                return equipeUsuario;
            }
        }
        return null;
    }

    private boolean contemAutorizacao (List<EquipeUsuario> equipeUsuarios, String request, Equipe equipe){
        System.out.println(request);
        if(usuarioPertenceEquipe(equipeUsuarios,equipe) !=null) {
            if (usuarioPertenceEquipe(equipeUsuarios, equipe).getCriador()) {
                return true;
            }
            for (Permissao permissao : usuarioPertenceEquipe(equipeUsuarios, equipe).getPermissao()) {
                if (permissao.getAuthority().equals(request)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verificaSeEquipeTemUsuario(Equipe equipe, List<EquipeUsuario> equipeUsuarios){
        for(EquipeUsuario equipeUsuario: equipeUsuarios){
            if(equipeUsuario.getEquipe().equals(equipe)){

                return true;
            }
        }
        return false;
    }

}
