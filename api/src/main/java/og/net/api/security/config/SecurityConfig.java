package og.net.api.security.config;

import lombok.AllArgsConstructor;
import og.net.api.model.entity.Permissao;
import og.net.api.security.acess.IsUser;
import og.net.api.security.acess.UsuarioDaEquipe;
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


@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;
    private final FiltroAutenticacao filtroAutenticacao;
    private final IsUser eUsuario;
    private final UsuarioDaEquipe usuarioDaEquipe;
    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        //Prevenção de ataque através de um token/ cria um token para poder identificar o usuario
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers(HttpMethod.POST,"/login").permitAll()
                .requestMatchers(HttpMethod.POST,"/usuario").permitAll()
                .requestMatchers(HttpMethod.GET,"/usuario").permitAll()
                .requestMatchers(HttpMethod.GET,"/usuario/{id}").access(eUsuario)
                .requestMatchers(HttpMethod.PUT,"/usuario/{id}").access(eUsuario)
                .requestMatchers(HttpMethod.DELETE, "/usuario/{id}").access(eUsuario)
                .requestMatchers(HttpMethod.POST, "/equipe").hasAuthority(Permissao.CRIAR.getAuthority())
                .requestMatchers(HttpMethod.GET, "/equipe/{id}").access(usuarioDaEquipe)
                .requestMatchers(HttpMethod.PUT, "/equipe/{id}").access(usuarioDaEquipe)
                .requestMatchers(HttpMethod.DELETE, "/equipe/{id}").access(usuarioDaEquipe)
                .requestMatchers(HttpMethod.PATCH, "/equipe/{id}").access(usuarioDaEquipe)
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
