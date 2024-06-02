package og.net.api.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import og.net.api.exception.DadosIncompletosException;
import og.net.api.exception.EquipeJaExistenteException;
import og.net.api.model.dto.EquipeCadastroDTO;
import og.net.api.model.dto.UsuarioCadastroDTO;
import og.net.api.model.entity.Equipe;
import og.net.api.model.entity.EquipeUsuario;
import og.net.api.model.entity.Permissao;
import og.net.api.model.entity.Usuario;
import og.net.api.repository.EquipeRepository;
import og.net.api.repository.ProjetoRepository;
import og.net.api.repository.TarefaRepository;
import og.net.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ApresentacaoService {
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final TarefaService tarefaService;
    private TarefaRepository tarefaRepository;
    private ProjetoRepository projetoRepository;
    private final ProjetoService projetoService;
    private final EquipeService equipeService;
    private final EquipeRepository equipeRepository;
    public void criaObjetosPadraoApresentacao() {
        criaUsuarios();
    }

    private void criaUsuarios(){
    UsuarioCadastroDTO antonio = new UsuarioCadastroDTO("Antônio","Oliveira","Antonio_Oliveira",
            new Date(73, Calendar.JUNE,10,10,0,0),"antonioOliveira@gmail.com","WEG","antonio123@",
            null,null,null,null,null,false,null);
        UsuarioCadastroDTO lucas = new UsuarioCadastroDTO("Lucas","Schutz","Lucas_Schutz",
                new Date(101, Calendar.JANUARY,10,10,0,0),"lucasSchutz@gmail.com","WEG","antonio123@",
                null,null,null,null,null,false,null);
        UsuarioCadastroDTO gabriela = new UsuarioCadastroDTO("Gabriela","Torres","Gabriela_Torres",
                new Date(105, Calendar.JANUARY,10,10,0,0),"gabrielaTorres@gmail.com","WEG","gabriela123@",
                null,null,null,null,null,false,null);
        Usuario antonioBanco =null;
        Usuario lucasBanco = null;
        Usuario gabrielaBanco = null;
        try {
            antonioBanco= usuarioService.cadastrar(antonio);
            lucasBanco= usuarioService.cadastrar(lucas);
            gabrielaBanco = usuarioService.cadastrar(gabriela);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Equipe rh= criaEquipespadrao("RH", "Equipe de Recursos Humanos");
        Equipe ti= criaEquipespadrao("TI", "Equipe de Tecnologia da Informação");
        Equipe redes= criaEquipespadrao("REDES", "Equipe de Serviço de Redes");

        List<EquipeUsuario> equipeUsuariosAntonio = getEquipeUsuariosAntonio(rh, ti, redes);

        antonioBanco.setEquipes(equipeUsuariosAntonio);

        List<EquipeUsuario> equipeUsuariosGabriela = getEquipeUsuariosGabriela(ti);


        gabrielaBanco.setEquipes(equipeUsuariosGabriela);

        List<EquipeUsuario> equipeUsuariosLucas = getEquipeUsuariosLucas(rh);

        lucasBanco.setEquipes(equipeUsuariosLucas);


        try {
            antonioBanco= usuarioService.cadastrar(antonio);
            lucasBanco= usuarioService.cadastrar(lucas);
            gabrielaBanco = usuarioService.cadastrar(gabriela);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }



    }

    private static List<EquipeUsuario> getEquipeUsuariosAntonio(Equipe rh, Equipe ti, Equipe redes) {
        EquipeUsuario equipeUsuarioRhAntonio = new EquipeUsuario(null, rh,
                List.of(Permissao.CRIAR,Permissao.VER,Permissao.EDITAR,Permissao.PATCH,Permissao.DELETAR),true);
        EquipeUsuario equipeUsuarioTiAntonio = new EquipeUsuario(null, ti,
                List.of(Permissao.CRIAR,Permissao.VER,Permissao.EDITAR,Permissao.PATCH,Permissao.DELETAR),true);
        EquipeUsuario equipeUsuarioRedesAntonio = new EquipeUsuario(null, redes,
                List.of(Permissao.CRIAR,Permissao.VER,Permissao.EDITAR,Permissao.PATCH,Permissao.DELETAR),true);

        return new ArrayList<>(List.of(equipeUsuarioRhAntonio, equipeUsuarioTiAntonio, equipeUsuarioRedesAntonio));
    }


    private static List<EquipeUsuario> getEquipeUsuariosGabriela(Equipe ti) {
        EquipeUsuario equipeUsuarioTiGabriela = new EquipeUsuario(null, ti,
                List.of(Permissao.CRIAR,Permissao.VER,Permissao.EDITAR,Permissao.PATCH,Permissao.DELETAR),false);
        return new ArrayList<>(List.of(equipeUsuarioTiGabriela));
    }

    private static List<EquipeUsuario> getEquipeUsuariosLucas(Equipe rh) {
        EquipeUsuario equipeUsuarioTiGabriela = new EquipeUsuario(null, rh,
                List.of(Permissao.CRIAR,Permissao.VER,Permissao.EDITAR,Permissao.PATCH,Permissao.DELETAR),false);
        return new ArrayList<>(List.of(equipeUsuarioTiGabriela));
    }

    private Equipe criaEquipespadrao(String nomeEquipe, String descricao){
        EquipeCadastroDTO equipeCadastroDTO = new EquipeCadastroDTO(nomeEquipe, descricao,null);
        try {
             return equipeService.cadastrar(equipeCadastroDTO);
        } catch (EquipeJaExistenteException e) {
            throw new RuntimeException(e);
        }
    }
}
