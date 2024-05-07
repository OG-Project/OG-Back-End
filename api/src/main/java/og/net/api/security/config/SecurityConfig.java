package og.net.api.security.config;

import lombok.AllArgsConstructor;
import og.net.api.model.entity.Permissao;
import og.net.api.security.acess.IsUser;
import og.net.api.security.acess.UsuarioDaEquipe;
import og.net.api.security.acess.UsuarioTemPermissaoEquipe;
import og.net.api.security.acess.UsuarioTemPermissaoProjeto;
import og.net.api.security.filter.FiltroAutenticacao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;
    private final FiltroAutenticacao filtroAutenticacao;
    private final IsUser eUsuario;
    private final UsuarioDaEquipe usuarioDaEquipe;
    private  final CorsConfigurationSource corsConfigurationSource;
    private final UsuarioTemPermissaoEquipe usuarioTemPermissaoEquipe;
    private final UsuarioTemPermissaoProjeto usuarioTemPermissaoProjeto;
    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource));
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // LOGIN

                .requestMatchers(HttpMethod.POST,"/login").permitAll()

                //USUARIO

                .requestMatchers(HttpMethod.POST,"/usuario").permitAll()
                .requestMatchers(HttpMethod.GET,"/usuario").permitAll()
                .requestMatchers(HttpMethod.GET,"/usuario/{id}").access(eUsuario)
                .requestMatchers(HttpMethod.PUT,"/usuario/{id}").access(eUsuario)
                .requestMatchers(HttpMethod.PATCH,"/usuario/{id}").access(eUsuario)
                .requestMatchers(HttpMethod.DELETE, "/usuario/{id}").access(eUsuario)
                .requestMatchers(HttpMethod.PATCH, "/usuario/add/{usuarioId}/{equipeId}/{permissaoId}").access(usuarioTemPermissaoEquipe)
                .requestMatchers(HttpMethod.GET, "/usuario/buscarMembros/{equipeId}").access(usuarioTemPermissaoEquipe)

                //EQUIPE
                .requestMatchers(HttpMethod.POST, "/equipe").hasAuthority(Permissao.CRIAR.getAuthority())
                .requestMatchers(HttpMethod.GET, "/equipe/{id}").access(usuarioDaEquipe)
                .requestMatchers(HttpMethod.PUT, "/equipe").access(usuarioDaEquipe)
                .requestMatchers(HttpMethod.DELETE, "/equipe/{id}").access(usuarioDaEquipe)
                .requestMatchers(HttpMethod.PATCH, "/equipe/{id}").access(usuarioDaEquipe)

                //PROJETO
                .requestMatchers(HttpMethod.GET,"/projeto").hasAuthority(Permissao.VER.getAuthority())
                .requestMatchers(HttpMethod.GET,"/projeto/{id}").hasAuthority(Permissao.VER.getAuthority())
                .requestMatchers(HttpMethod.POST, "/projeto").hasAuthority(Permissao.CRIAR.getAuthority())
                .requestMatchers(HttpMethod.POST, "/projeto/{equipeId}").access(usuarioTemPermissaoEquipe)
                .requestMatchers(HttpMethod.PUT, "/projeto").hasAuthority(Permissao.EDITAR.getAuthority())
                .requestMatchers(HttpMethod.PUT, "/projeto/{equipeId}").access(usuarioTemPermissaoEquipe)
                .requestMatchers(HttpMethod.GET, "/projeto/buscarProjetos/{equipeId}").access(usuarioTemPermissaoEquipe)
                .requestMatchers(HttpMethod.DELETE, "/projeto/removerProjetoEquipe/{equipeId}/{projetoId}").access(usuarioTemPermissaoProjeto)
                .requestMatchers(HttpMethod.DELETE, "/projeto/deletarPropriedade/{idPropriedade}/{idProjeto}").access(usuarioTemPermissaoProjeto)

                // Notificação

                .requestMatchers(HttpMethod.GET, "/notificacao/buscar/{recptorId}").permitAll()
                .requestMatchers(HttpMethod.POST, "/notificacao/projeto").permitAll()
                .requestMatchers(HttpMethod.POST, "/notificacao/equipe").permitAll()
                .requestMatchers(HttpMethod.POST, "/notificacao/convite/equipe").permitAll()
                .requestMatchers(HttpMethod.POST, "/notificacao/convite/projeto").permitAll()
                .requestMatchers(HttpMethod.GET, "/notificacao/conviteEquipe/{equipeId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/notificacao/conviteProjeto/{projetoId}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/notificacao/buscar/{recptorId}").permitAll()
                .anyRequest().authenticated());





        http.securityContext((context)-> context.securityContextRepository(securityContextRepository));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout((Customizer.withDefaults()));
        http.sessionManagement(config ->{
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.addFilterBefore(filtroAutenticacao, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
