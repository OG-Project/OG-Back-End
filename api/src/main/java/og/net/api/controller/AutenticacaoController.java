package og.net.api.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import og.net.api.exception.DadosIncompletosException;
import og.net.api.model.dto.UsuarioCadastroDTO;
import og.net.api.model.dto.UsuarioLoginDto;
import og.net.api.model.entity.Usuario;
import og.net.api.security.utils.CookieUtil;
import og.net.api.security.utils.JwtUtil;
import og.net.api.service.AutenticacaoService;
import og.net.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final AutenticacaoService autenticacaoService;
    private CookieUtil cookieUtil;
    private JwtUtil jwtUtil;
    private UsuarioService usuarioService;
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody UsuarioLoginDto usuarioLogin, HttpServletRequest request, HttpServletResponse response){
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioLogin.getUsername(), usuarioLogin.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Cookie cookie = cookieUtil.gerarCookieJwt(userDetails);
            response.addCookie(cookie);

            return ResponseEntity.ok(cookie);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
        }
    }


    @GetMapping("/cookie")
    public ResponseEntity<?> pegaUsuarioCookie(HttpServletResponse response, HttpServletRequest request){
        try {
            Cookie cookie= cookieUtil.getCookie(request, "JWT");
            String token= cookie.getValue();
            String username = jwtUtil.getUsername(token);
            Usuario usuario = usuarioService.buscarUsuariosUsername(username).get();
            return new ResponseEntity<>(usuario,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    public void loginComGoogle(HttpServletRequest
                                       request, HttpServletResponse response,
                               Authentication authentication) throws IOException {
        OAuth2User auth2User= (OAuth2User) authentication.getPrincipal();

       String email= auth2User.getAttribute("email");
       try {
           UserDetails  userDetails = autenticacaoService.loadUserByEmail(email);
           Cookie cookie =cookieUtil.gerarCookieJwt(userDetails);
           response.addCookie(cookie);

       }catch (Exception e){
           UsuarioCadastroDTO usuario = new UsuarioCadastroDTO(auth2User);
           usuario.setIsGoogleLogado(true);
           Usuario usuario1 = null;
           try {
               usuario1 = usuarioService.cadastrar(usuario);
           } catch (DadosIncompletosException ex) {
               throw new RuntimeException(ex);
           }
           Cookie cookie = cookieUtil.gerarCookieJwt(usuario1.getUsuarioDetailsEntity());
           response.addCookie(cookie);
       }

       response.sendRedirect("http://localhost:5173");
    }
}
