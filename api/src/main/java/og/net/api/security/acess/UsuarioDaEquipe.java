package og.net.api.security.acess;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.model.entity.*;
import og.net.api.repository.UsuarioRepository;
import og.net.api.security.HttpRequestConfig.CustomHttpServletRequestWrapper;
import og.net.api.service.EquipeService;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class UsuarioDaEquipe implements AuthorizationManager<RequestAuthorizationContext> {

    private EquipeService equipeService;
    private final ObjectMapper objectMapper;
    private final UsuarioRepository usuarioRepository;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Map<String, String> variables = object.getVariables();
        String request = object.getRequest().getMethod();
        Equipe equipe;
        if(variables.get("id")!=null) {
            Integer equipeId = Integer.parseInt(variables.get("id"));
            boolean autorizado = false;
            try {
                equipe = equipeService.buscarUm(equipeId);
            } catch (EquipeNaoEncontradaException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            equipe= transformaBodyEmEquipe(object.getRequest());
        }
        UsuarioDetailsEntity usuarioDetailsEntity = (UsuarioDetailsEntity) authentication.get().getPrincipal();
        Usuario usuario = usuarioRepository.findByUsername(usuarioDetailsEntity.getUsername()).get();
        return new AuthorizationDecision(contemAutorizacao(usuario,request,equipe));
    }

    private EquipeUsuario usuarioPertenceEquipe(List<EquipeUsuario> equipeUsuarios, Equipe equipe){
        for (EquipeUsuario equipeUsuario : equipeUsuarios){
            if(Objects.equals(equipeUsuario.getEquipe().getId(), equipe.getId())){
                return equipeUsuario;
            }
        }
        return null;
    }

    private boolean contemAutorizacao (Usuario usuario, String request, Equipe equipe){
        if(usuarioPertenceEquipe(usuario.getEquipes(),equipe) !=null) {
            if(usuarioPertenceEquipe(usuario.getEquipes(),equipe).getCriador()){
                return true;
            }
            for (Permissao permissao : usuarioPertenceEquipe(usuario.getEquipes(), equipe).getPermissao()) {
                if (permissao.getAuthority().equals(request)) {
                    return true;
                }
            }

        }
        return false;
    }

    private Equipe transformaBodyEmEquipe(HttpServletRequest httpRequest) {
        try {
            HttpServletRequest wrappedRequest = new CustomHttpServletRequestWrapper(httpRequest);
            try (BufferedReader reader = wrappedRequest.getReader()) {
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String requestBody = stringBuilder.toString();
                return objectMapper.readValue(requestBody, Equipe.class);
            } catch (IOException e) {
                e.printStackTrace(); // Tratar o erro de forma adequada
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace(); // Tratar o erro de forma adequada
            return null;
        }
    }
}
