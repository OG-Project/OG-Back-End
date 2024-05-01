package og.net.api.model.entity;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@AllArgsConstructor
public enum Permissao implements GrantedAuthority {
    VER ("GET"),
    EDITAR ("PUT"),
    DELETAR ("DELETE"),
    CRIAR ("POST"),

    PATCH ("PATCH")
    ;
    private final String nome;
    @Override
    public String getAuthority() {
        return name();
    }
}
