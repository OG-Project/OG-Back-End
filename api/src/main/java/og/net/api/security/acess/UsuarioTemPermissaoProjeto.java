package og.net.api.security.acess;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.AllArgsConstructor;
import og.net.api.model.dto.ProjetoCadastroDTO;
import og.net.api.model.entity.*;
import og.net.api.repository.ProjetoRepository;
import og.net.api.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class UsuarioTemPermissaoProjeto implements AuthorizationManager<RequestAuthorizationContext> {
    private final ObjectMapper objectMapper;
    private final ProjetoRepository projetoRepository;
    private final ModelMapper modelMapper;
    private final UsuarioRepository usuarioRepository;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        // passar como parametro o id da equipe quando o usuario estiver criando um projeto por uma equipe.
        Projeto projeto;
        Map<String, String> variables = object.getVariables();
        Integer projetoId= setProjetoId(variables);
        if(projetoId ==-1){
             projeto =transformaBodyEmProjeto(object.getRequest());

        }else{
            projeto = projetoRepository.findById(projetoId).get();

        }

        String request = object.getRequest().getMethod();
        UsuarioDetailsEntity usuarioDetailsEntity = (UsuarioDetailsEntity) authentication.get().getPrincipal();
        Usuario usuario = usuarioRepository.findByUsername(usuarioDetailsEntity.getUsername()).get();
        return new AuthorizationDecision(verificaAutorizacaoDentroEquipe(projeto,usuario, request));
    }

    private Projeto transformaBodyEmProjeto(HttpServletRequest httpRequest){
        try {
            InputStream inputStream = httpRequest.getInputStream();
            return  objectMapper.readValue(inputStream, Projeto.class);
        } catch (IOException e) {
            return null;
        }
    }

    private boolean verificaAutorizacaoDentroEquipe(Projeto projeto, Usuario usuario , String request){
        boolean verificacao = verificaUsuarioResponsavelProjeto(projeto, usuario);
        for(ProjetoEquipe projetoEquipe:projeto.getProjetoEquipes()){
           for (EquipeUsuario equipeUsuario:usuario.getEquipes()){
               if(equipeUsuario.getEquipe().equals(projetoEquipe.getEquipe())){
                   verificacao= equipeUsuario.getPermissao().stream().anyMatch(permissao -> permissao.getAuthority().equals(request));
               }
           }
       }
       return verificacao;
    }

    private Integer setProjetoId( Map<String, String> variables){
       if(variables.get("projetoId") != null){
          return Integer.parseInt(variables.get("projetoId"));
       }else if(variables.get("idProjeto") != null){
           return  Integer.parseInt(variables.get("idProjeto"));
       }else if(variables.get("id") !=null) {
           return Integer.parseInt(variables.get("id"));
       }
       return -1;
    }

    private  boolean verificaUsuarioResponsavelProjeto(Projeto projeto, Usuario usuario){
        for(UsuarioProjeto usuarioProjeto : projeto.getResponsaveis()){
            if(usuarioProjeto.getIdResponsavel() == usuario.getId()){
                return  true;
            }
        }
        return false;
    }
}
