package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.*;
import og.net.api.model.dto.*;
import og.net.api.model.entity.*;
import og.net.api.repository.EquipeUsuarioRepository;
import og.net.api.repository.UsuarioDetailsEntityRepository;
import og.net.api.repository.UsuarioRepository;
import og.net.api.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private EquipeService equipeService;
    private UsuarioProjetoRepository usuarioProjetoRepository;
    private UsuarioAceitoRepository usuarioAceitoRepository;
    private ComentarioRepository comentarioRepository;
    private UsuarioTarefaRepository usuarioTarefaRepository;
    private EquipeUsuarioRepository equipeUsuarioRepository;
    private ModelMapper modelMapper;
    private final UsuarioDetailsEntityRepository usuarioDetailsEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private ProjetoRepository projetoRepository;
    private TarefaService tarefaService;

    public Usuario buscarUm(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public void deletar(Integer id) {
        Usuario usuario=usuarioRepository.findById(id).get();
        comentarioRepository.deleteAllByAutor_Id(usuario.getId());
        usuarioAceitoRepository.deleteAllByUsuario_Id(id);

        usuarioProjetoRepository.deleteByIdResponsavel(id);
        for (EquipeUsuario equipe : usuario.getEquipes()) {
            equipeUsuarioRepository.deleteByEquipe(equipe.getEquipe());
        }
        usuarioRepository.deleteById(id);
    }

    public void enviaEmail(Usuario usuario) {
        try {
            Properties properties = System.getProperties();

            // define os dados básicos
            String host = "smtp.gmail.com";
            String email = "og.project.weg@gmail.com";
            String password = "sryx rozm wixy iool" ;

            //liga os protocolo tudo
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.ssl.trust", host);

            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email, "OG TESTE MLK"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
                message.setSubject("Confirme Seu Email!");

                String htmlContent = "<html><body style='background-color: #f6f8fa;'>" +
                        "<div style='margin: 0 auto; max-width: 600px; padding: 20px; text-align: center;'>" +
                        "<img src='api/src/main/resources/logo.jpg' " +
                        "alt='OG LOGO' style='margin-bottom: 20px; width: 140px; height: auto;'>" +
                        "<div style='background-color: #fff; border-radius: 6px; padding: 40px;'>" +
                        "<p style='font-size: 14px; color: #586069;'>" + usuario.getUsername() + " </p>" +
                        "<h1 style='font-size: 36px; color: #092C4C; margin-top: 10px; margin-bottom: 30px;'>  </h1>" +
                        "<h2 style='color: #092C4C; font-weight: 500;'><a href='http://localhost:8082" + usuario.getEmail() + "'>Confirmar E-mail</a></h2>" +
                        "</div>" +
                        "</div>" +
                        "</body></html>";

                message.setContent(htmlContent, "text/html; charset=utf-8");

                Transport.send(message);
            } catch (MessagingException mex) {
                mex.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.out.println("Falha E-mail");
        }
    }

    public Usuario cadastrar(IDTO dto) throws IOException, DadosIncompletosException {
        UsuarioCadastroDTO usuarioCadastroDTO = (UsuarioCadastroDTO) dto;
        Usuario usuario = new Usuario();
        Configuracao configuracao = configuracaoPadrao();
        usuarioCadastroDTO.setConfiguracao(configuracao);
        modelMapper.map(usuarioCadastroDTO, usuario);

        fotoPadrao(usuario);
        usuario.setSenha(passwordEncoder.encode(usuarioCadastroDTO.getSenha()));
        try {
            usuarioRepository.save(usuario);
//            enviaEmail(usuario);
        } catch (Exception e) {
            throw new DadosIncompletosException();
        }
        List<EquipeUsuario> equipesUsuario = equipePadrao(usuario);
        usuario.setEquipes(equipesUsuario);
        usuarioRepository.save(usuario);
         equipesUsuario.forEach(equipeUsuario -> {
             projetoPadrao(equipeUsuario.getEquipe(),usuario);
         });
        usuario.setEquipes(equipesUsuario);
        return usuarioRepository.save(usuario);
    }


    private Configuracao configuracaoPadrao(){
        Configuracao configuracao = new Configuracao();
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
        configuracao.setIsTutorial(true);
//        configuracao.setIsTutorialAtivo();

        return configuracao;
    }
    private List<EquipeUsuario> equipePadrao(Usuario usuario)  {
        EquipeCadastroDTO equipeCadastroDTO = new EquipeCadastroDTO(("Equipe do "+usuario.getUsername()), "Está é sua equipe para organização pessoal",null);
        List<EquipeUsuario> equipeUsuarios = new ArrayList<>();
        EquipeUsuario equipeUsuario = new EquipeUsuario();
        Equipe equipe = null;
        try {
            equipe = equipeService.cadastrar(equipeCadastroDTO);
        } catch (EquipeJaExistenteException e) {
            throw new RuntimeException(e);
        }
        equipeUsuario.setEquipe(equipe);
        equipeUsuario.setCriador(true);
        equipeUsuario.setPermissao(List.of(Permissao.CRIAR,Permissao.VER,Permissao.EDITAR,Permissao.PATCH));
        equipeUsuarios.add(equipeUsuario);

        return equipeUsuarios;
    }


    private void projetoPadrao(Equipe equipe, Usuario usuario ){
        List<Status> statusPadrao = new ArrayList<>();
        Status naoIniciado = new Status("Não iniciado", "36213E");
        Status pronto = new Status("Pronto", "38a31a");
        Status emProgresso = new Status("Em Progresso", "17179c");
        statusPadrao.add(pronto);
        statusPadrao.add(emProgresso);
        statusPadrao.add(naoIniciado);
        Projeto projeto = new Projeto(statusPadrao);
        projetoRepository.save(projeto);
        UsuarioTarefa usuarioTarefa = new UsuarioTarefa(null, usuario.getId());
        List<UsuarioTarefa> usuarioTarefaList = new ArrayList<>();
        usuarioTarefaList.add(usuarioTarefa);

        TarefaCadastroDTO tarefaCadastroDTO = new TarefaCadastroDTO("Ler um livro.",
                "Já leu um pouco hoje? tire 10 minutinhos para ler um livro hoje.",
                true,
                LocalDateTime.now(),
                "263478",
                null,
                naoIniciado,
                null,usuarioTarefaList);

        TarefaCadastroDTO tarefaCadastroDTO2 = new TarefaCadastroDTO("Fazer uma afirmação positiva.",
                "Faça uma afirmação positiva sobre você mesmo ou sobre o próximo o dia fica mais leve assim.",
                true,
                LocalDateTime.now(),
                "b36302",
                null,
                emProgresso,
                null,usuarioTarefaList);

        TarefaCadastroDTO tarefaCadastroDTO3 = new TarefaCadastroDTO("Olhe suas tarefas diárias.",
                "Essa tarefa você já conclui, parabéns seu dia vai ser um sucesso.",
                true,
                LocalDateTime.now(),
                "600573",
                null,
                pronto,
                null,usuarioTarefaList);

        Tarefa tarefaPadrao1= null;
        Tarefa tarefaPadrao2= null;
        Tarefa tarefaPadrao3= null;
        try {
           tarefaPadrao1= tarefaService.cadastrar(tarefaCadastroDTO,projeto.getId());
           tarefaPadrao2 =tarefaService.cadastrar(tarefaCadastroDTO2,projeto.getId());
           tarefaPadrao3= tarefaService.cadastrar(tarefaCadastroDTO3,projeto.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




        List<Tarefa> tarefasPadroes = new ArrayList<>();

        tarefasPadroes
                .add(tarefaPadrao1);
        tarefasPadroes
                .add(tarefaPadrao2);
        tarefasPadroes
                .add(tarefaPadrao3);

        List<ProjetoEquipe> projetoEquipes = new ArrayList<>();
        projetoEquipes.add(new ProjetoEquipe(equipe));

        List<UsuarioProjeto> usuarioProjetos = new ArrayList<>();
        usuarioProjetos.add(new UsuarioProjeto(usuario.getId(), List.of(Permissao.CRIAR,Permissao.VER,Permissao.EDITAR,Permissao.PATCH,Permissao.DELETAR)));
        ProjetoCadastroDTO projetoCadastroDTO = new ProjetoCadastroDTO("Rotina","Aqui você pode manter suas tarefas diarias",
                LocalDateTime.now(),
                statusPadrao,
                tarefasPadroes,
                null,
                projetoEquipes,
                null,
                null,
                "meus-projetos"
                );
        projetoCadastroDTO.setResponsaveis(usuarioProjetos);
        projetoCadastroDTO.setProjetoEquipes(projetoEquipes);
        projetoRepository.save(projeto);
        modelMapper.map(projetoCadastroDTO,projeto);
        projeto.setProjetoEquipes(projetoEquipes);
        try {
           Projeto projeto1= projetoRepository.save(projeto);
           System.out.println(projeto1.getDataCriacao());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

    public Optional<Usuario> buscarUsuariosUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Optional<Usuario> buscarUsuariosEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    public Usuario editar(IDTO dto) throws DadosNaoEncontradoException {
        UsuarioEdicaoDTO ucdto = (UsuarioEdicaoDTO) dto;
        Usuario usuarioBusca = usuarioRepository.findById(ucdto.getId()).get();
        UsuarioDetailsEntity usuarioDetailsEntity = usuarioDetailsEntityRepository.findByUsuario(usuarioBusca);
        Usuario usuario = new Usuario(usuarioDetailsEntity);
        modelMapper.map(ucdto, usuario);
        if (usuarioRepository.existsById(usuario.getId())) {
            usuarioRepository.save(usuario);
            return usuario;
        }
        throw new DadosNaoEncontradoException();
    }

    public void adicionarAEquipe(Integer userId, Integer equipeId,Integer permissaoId) {
        try {
            Equipe equipe = equipeService.buscarUm(equipeId);
            Usuario user = buscarUm(userId);

            EquipeUsuario equipeUsuario = new EquipeUsuario();
            equipeUsuario.setEquipe(equipe);
            if(permissaoId == 1){
                equipeUsuario.setPermissao(List.of(Permissao.CRIAR, Permissao.EDITAR, Permissao.PATCH, Permissao.VER));
            } else if (permissaoId == 2) {
                equipeUsuario.setPermissao(List.of(Permissao.VER));
            }
            user.getEquipes().add(equipeUsuario);
            usuarioRepository.save(user);

        } catch (EquipeNaoEncontradaException e) {
            e.printStackTrace();
        }
    }

    public void adicionaCriador(Integer userId, Integer equipeId){
        try {
            Equipe equipe = equipeService.buscarUm(equipeId);
            Usuario user = buscarUm(userId);

            EquipeUsuario equipeUsuario = new EquipeUsuario();
            equipeUsuario.setEquipe(equipe);
            equipeUsuario.setCriador(true);
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
