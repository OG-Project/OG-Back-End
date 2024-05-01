package og.net.api.security.acess;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import og.net.api.model.entity.*;
import og.net.api.repository.ProjetoRepository;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
@Component
@AllArgsConstructor
public class UsuarioTemPermissaoProjeto implements AuthorizationManager<RequestAuthorizationContext> {
    private final ObjectMapper objectMapper;
    private final ProjetoRepository projetoRepository;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Projeto projeto= transformaBodyEmProjeto(object.getRequest());
        Map<String, String> variables = object.getVariables();
        String request = object.getRequest().getMethod();
        Integer projetoId;
        UsuarioDetailsEntity usuarioDetailsEntity = (UsuarioDetailsEntity) authentication.get().getPrincipal();
        if(request.equals("DELETE")){
            projetoId= setProjetoId(object.getRequest(), variables);
            projeto = projetoRepository.findById(projetoId).get();
        }
        return new AuthorizationDecision(verificaAutorizacaoDentroEquipe(projeto.getProjetoEquipes(),usuarioDetailsEntity.getUsuario(), request));
    }

    private Projeto transformaBodyEmProjeto(HttpServletRequest httpRequest){
        String requestBody = null;
        try (BufferedReader reader = httpRequest.getReader()) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            requestBody = stringBuilder.toString();
             return objectMapper.readValue(requestBody, Projeto.class);
        } catch (IOException e) {
            return null;
        }
    }

    private boolean verificaAutorizacaoDentroEquipe(List<ProjetoEquipe> equipes, Usuario usuario , String request){
        boolean verificacao = false;
       for(ProjetoEquipe projetoEquipe:equipes){
           for (EquipeUsuario equipeUsuario:usuario.getEquipes()){
               if(equipeUsuario.getEquipe().equals(projetoEquipe.getEquipe())){
                   verificacao= equipeUsuario.getPermissao().stream().anyMatch(permissao -> permissao.getAuthority().equals(request));
               }
           }
       }
       return verificacao;
    }

    private Integer setProjetoId(HttpServletRequest request, Map<String, String> variables){
       Integer projetoId = Integer.parseInt(variables.get("projetoId"));
        if(request.getRequestURI().equals("/projeto/deletarPropriedade/{idPropriedade}/{idProjeto}")){
            projetoId = Integer.parseInt(variables.get("idProjeto"));
        }else if(request.getRequestURI().equals("/projeto/{id}")){
            projetoId = Integer.parseInt(variables.get("id"));
        }
        return projetoId;
    }
}
