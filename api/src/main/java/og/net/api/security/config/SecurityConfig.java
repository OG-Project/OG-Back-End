package og.net.api.security.config;

import lombok.AllArgsConstructor;
import og.net.api.controller.AutenticacaoController;
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
    private final AutenticacaoController autenticacaoController;
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
                .requestMatchers(HttpMethod.PATCH, "/usuario/add/{usuarioId}/{equipeId}/{permissaoId}").hasAuthority(Permissao.PATCH.getAuthority())
                .requestMatchers(HttpMethod.GET, "/usuario/buscarMembros/{equipeId}").hasAuthority(Permissao.VER.getAuthority())

                //EQUIPE
                .requestMatchers(HttpMethod.POST, "/equipe").hasAuthority(Permissao.CRIAR.getAuthority())
                .requestMatchers(HttpMethod.GET, "/equipe/{id}").access(usuarioDaEquipe)
                .requestMatchers(HttpMethod.PUT, "/equipe").hasAuthority(Permissao.EDITAR.getAuthority())
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
                .requestMatchers(HttpMethod.PUT,"/notificacao").permitAll()
                .requestMatchers(HttpMethod.POST, "/notificacao/convite/projeto").permitAll()
                .requestMatchers(HttpMethod.GET, "/notificacao/conviteEquipe/{equipeId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/notificacao/conviteProjeto/{projetoId}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/notificacao/buscar/{recptorId}").permitAll()

                // Historico
                .requestMatchers(HttpMethod.POST,"/historico").permitAll()
                .requestMatchers(HttpMethod.GET, "/historico/tarefa/{tarefaId}").permitAll()
                .requestMatchers(HttpMethod.GET, "/historico/projeto/{projetoId}").permitAll()

                .requestMatchers(HttpMethod.PATCH, "/tarefa/arquivos/{id}").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/tarefa/arquivos/{id}").permitAll()

                .requestMatchers(HttpMethod.DELETE, "/mensagem").hasAuthority(Permissao.DELETAR.getAuthority())
                .requestMatchers(HttpMethod.POST, "/mensagem").hasAuthority(Permissao.CRIAR.getAuthority())
                .requestMatchers(HttpMethod.GET, "/mensagem/{id}").hasAuthority(Permissao.VER.getAuthority())

                .anyRequest().authenticated()).oauth2Login(httpAuth2 -> httpAuth2.successHandler(autenticacaoController::loginComGoogle));

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
