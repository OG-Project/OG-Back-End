package og.net.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Builder
public class UsuarioDetailsEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private  Usuario usuario;
    private String password;
    @Column(unique = true,nullable = false,updatable = false)
    private String username;
    private boolean enabled;
    private boolean AccountNonExpired;
    private boolean AccountNonLocked;
    private boolean CredentialsNonExpired;
    private Collection<Permissao> authorities;

}
