package og.net.api.security.utils;

import og.net.api.model.entity.Permissao;
import og.net.api.model.entity.UsuarioDetailsEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ContemAutorizacaoUtil {
    public boolean contemAutorizacao(String request, UsuarioDetailsEntity usuarioDetailsEntity){
        Map<String, Boolean> allRequest = new HashMap<>();
        allRequest.put("GET", usuarioDetailsEntity.getAuthorities().contains(Permissao.VER));
        allRequest.put("PUT", usuarioDetailsEntity.getAuthorities().contains(Permissao.EDITAR));
        allRequest.put("DELETE", usuarioDetailsEntity.getAuthorities().contains(Permissao.DELETAR));

        return allRequest.get(request);
    }
}
