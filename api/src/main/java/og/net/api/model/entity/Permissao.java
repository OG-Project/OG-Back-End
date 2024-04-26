package og.net.api.model.entity;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@AllArgsConstructor
public enum Permissao implements GrantedAuthority {
    VER ("Get"),
    EDITAR ("Put"),
    DELETAR ("Delete"),
    CRIAR ("Post"),
    ;
    private final String nome;
    @Override
    public String getAuthority() {
        return name();
    }
}
