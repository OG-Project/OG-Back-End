package og.net.api.service;

import com.sun.tools.jconsole.JConsoleContext;
import lombok.AllArgsConstructor;
import og.net.api.exception.*;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.UsuarioCadastroDTO;
import og.net.api.model.dto.UsuarioEdicaoDTO;
import og.net.api.model.entity.*;
import og.net.api.repository.EquipeUsuarioRepository;
import og.net.api.repository.UsuarioProjetoRepository;
import og.net.api.repository.UsuarioRepository;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.IllegalFormatCodePointException;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private EquipeService equipeService;
    private EquipeUsuarioRepository equipeUsuarioRepository;
    private ModelMapper modelMapper;

    public Usuario buscarUm(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public void deletar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto) throws IOException {
        UsuarioCadastroDTO usuarioCadastroDTO = (UsuarioCadastroDTO) dto;
        Usuario usuario = new Usuario();
        Configuracao configuracao=new Configuracao();
        configuracao.setFonteCorpo("Poppins");
        configuracao.setFonteTitulo("Source Sans 3");
        configuracao.setFonteCorpoTamanho(2.0);
        configuracao.setFonteTituloTamanho(6);
        configuracao.setIdioma("Portugues");
        configuracao.setHueCor("273");
        configuracao.setIsDigitarVoz(false);
        configuracao.setIsLibras(false);
        configuracao.setIsTecladoVirtual(false);
        configuracao.setIsVisualizaEmail(true);
        configuracao.setIsVisualizaEquipes(true);
        configuracao.setIsVisualizaPerfil(true);
        configuracao.setIsVisualizaProjetos(true);
        configuracao.setIsDark(false);
        usuarioCadastroDTO.setConfiguracao(configuracao);
        
        modelMapper.map(usuarioCadastroDTO, usuario);
        fotoPadrao(usuario);
        usuarioRepository.save(usuario);
    }

    private Usuario fotoPadrao(Usuario usuario) throws IOException {
        ClassPathResource resource = new ClassPathResource ("fotoPadraoDoUsuario.png");
        byte[] content = Files.readAllBytes(resource.getFile().toPath());
        Arquivo result = new Arquivo("fotoPadraoUsuario.png", content,"image/png");
        usuario.setFoto(result);
       return usuarioRepository.save(usuario);

    }

    public List<Usuario> buscarUsuariosNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public List<Usuario> buscarUsuariosUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public List<Usuario> buscarUsuariosEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    public Usuario editar(IDTO dto) throws DadosNaoEncontradoException {
        UsuarioEdicaoDTO ucdto = (UsuarioEdicaoDTO) dto;
        Usuario usuario = new Usuario();
        System.out.println();
        modelMapper.map(ucdto, usuario);
        if (usuarioRepository.existsById(usuario.getId())) {
            usuarioRepository.save(usuario);
            return usuario;
        }
        throw new DadosNaoEncontradoException();
    }

    public void adicionarAEquipe(Integer userId, Integer equipeId) {
        try {
            Equipe equipe = equipeService.buscarUm(equipeId);
            Usuario user = buscarUm(userId);

            EquipeUsuario equipeUsuario = new EquipeUsuario();
            equipeUsuario.setEquipe(equipe);
            user.getEquipes().add(equipeUsuario);
            usuarioRepository.save(user);

        } catch (EquipeNaoEncontradaException e) {
            e.printStackTrace();
        }
    }

        public void atualizarFoto(Integer id, MultipartFile foto) throws IOException {
        Usuario usuario = buscarUm(id);
        usuario.setFoto(new Arquivo(foto));
        usuarioRepository.save(usuario);
    }

    public void adicionarmembros(List<Integer> ids, Integer equipeId) {
        try {
            Equipe equipe = equipeService.buscarUm(equipeId);

            ids.forEach(id -> {
                try {
                    Usuario user = buscarUm(id);
                    EquipeUsuario equipeUsuario = new EquipeUsuario();
                    equipeUsuario.setEquipe(equipe);
                    //setar as permissões
                    user.getEquipes().add(equipeUsuario);
                    usuarioRepository.save(user);
                } catch (Exception ignored) {
                }
            });


        } catch (EquipeNaoEncontradaException e) {
            e.printStackTrace();
        }
    }


    public List<Usuario> buscarMembrosEquipe(Integer equipeId) throws EquipeNaoEncontradaException {
        Equipe equipe = equipeService.buscarUm(equipeId);
        return equipeUsuarioRepository.findAllByEquipe(equipe).stream().map(
                eu -> usuarioRepository.findByEquipesContaining(eu)).toList();
    }

    public void removerUsuarioDaEquipe(Integer equipeId, Integer userId) {
        Usuario membroEquipe = buscarUm(userId);

        for (EquipeUsuario equipeUsuario : membroEquipe.getEquipes()) {
            if (equipeUsuario.getEquipe().getId().equals(equipeId)) {
                membroEquipe.getEquipes().remove(equipeUsuario);
                break;
            }
        }
        usuarioRepository.save(membroEquipe);
    }
}
