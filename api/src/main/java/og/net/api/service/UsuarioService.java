package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.UsuarioInesxistenteException;
import og.net.api.exception.UsuarioJaExistenteException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.UsuarioCadastroDTO;
import og.net.api.model.dto.UsuarioEdicaoDTO;
import og.net.api.model.entity.Usuario;
import og.net.api.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.IllegalFormatCodePointException;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

     private UsuarioRepository usuarioRepository;

    public Usuario buscarUm(Integer id) {
            return usuarioRepository.findById(id).get();
    }

    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
    }

    public void deletar(Integer id){
        usuarioRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto) {
        UsuarioCadastroDTO usuarioCadastroDTO = (UsuarioCadastroDTO) dto;
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioCadastroDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> buscarUsuariosNome(String nome){
        return usuarioRepository.findByNome(nome);
    }

    public List<Usuario> buscarUsuariosUsername(String username){
        return usuarioRepository.findByUsername(username);
    }

    public List<Usuario> buscarUsuariosEmail(String email){
        return usuarioRepository.findByEmail(email);
    }


    public Usuario editar(IDTO dto) throws DadosNaoEncontradoException {
        UsuarioEdicaoDTO ucdto = (UsuarioEdicaoDTO) dto;
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(ucdto,usuario);
       if (usuarioRepository.existsById(usuario.getId())){
           usuarioRepository.save(usuario);
           return usuario;
       }
       throw new DadosNaoEncontradoException();
    }
}
