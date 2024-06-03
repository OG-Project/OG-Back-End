package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosIncompletosException;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.EquipeJaExistenteException;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.model.dto.EquipeCadastroDTO;
import og.net.api.model.dto.ProjetoCadastroDTO;
import og.net.api.model.dto.TarefaCadastroDTO;
import og.net.api.model.dto.UsuarioCadastroDTO;
import og.net.api.model.entity.*;
import og.net.api.repository.EquipeRepository;
import og.net.api.repository.ProjetoRepository;
import og.net.api.repository.TarefaRepository;
import og.net.api.repository.UsuarioRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
          antonioBanco= usuarioRepository.save(antonioBanco);
          lucasBanco= usuarioRepository.save(lucasBanco);
          gabrielaBanco= usuarioRepository.save(gabrielaBanco);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        UsuarioProjeto usuarioProjetoAntonio = new UsuarioProjeto(antonioBanco.getId(),
                List.of(Permissao.CRIAR,Permissao.VER,Permissao.EDITAR,Permissao.DELETAR,Permissao.PATCH));
        UsuarioProjeto usuarioProjetoLucas = new UsuarioProjeto(lucasBanco.getId(),
                List.of(Permissao.CRIAR,Permissao.VER,Permissao.EDITAR,Permissao.DELETAR,Permissao.PATCH));

        List<UsuarioProjeto> responsaveisProjetoRH = new ArrayList<>(List.of(usuarioProjetoAntonio, usuarioProjetoLucas));
       Projeto projetoRh = criaProjetoPadraoRh(rh, responsaveisProjetoRH);
        criaTarefaSelecaoCandidatosRh(projetoRh,antonioBanco);
        criaTarefaProvasRh(projetoRh,antonioBanco);
        try {
            criaTarefaEntrevistasRh(projetoRh,antonioBanco,lucasBanco);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        List<UsuarioProjeto> responsaveisProjetoTI= new ArrayList<>(List.of(usuarioProjetoAntonio));

        Projeto projetoTi= criProjetoPadraoTi(ti,responsaveisProjetoTI);
        criaTarefaDiretrizesEmpresa(projetoTi, gabrielaBanco);
        criaTarefaEntregaDoModeloRelacionalBanco(projetoTi, gabrielaBanco);
        criaTarefaCriacaoBroker(projetoTi,antonioBanco);
        criaTarefaInterceptacaoRequestNode(projetoTi,antonioBanco);
    }

    private void criaTarefaDiretrizesEmpresa(Projeto projetoRh, Usuario usuario) {
        Status emProgresso = new Status();
        for (Status status: projetoRh.getStatusList()){
            if(status.getNome().equals("Em Progresso")){
                emProgresso = status;
            }
        }

        UsuarioTarefa usuarioTarefa = new UsuarioTarefa(null, usuario.getId());
        List<UsuarioTarefa> usuarioTarefaList = new ArrayList<>();
        usuarioTarefaList.add(usuarioTarefa);
        List<ValorPropriedadeTarefa> valorPropriedadeTarefas = new ArrayList<>();

        for (Propriedade propriedade: projetoRh.getPropriedades()){
            Data dataEntrega= new Data();
            LocalDate localDate;
            dataEntrega.setData(LocalDateTime.of(LocalDate.of(124,6,3), LocalTime.of(13,40,0)));
            ValorPropriedadeTarefa valorPropriedadeTarefa = new ValorPropriedadeTarefa(null,propriedade,dataEntrega,true);
            valorPropriedadeTarefas.add(valorPropriedadeTarefa);
        }
        SubTarefa subTarefa1 = new SubTarefa(null,"Ler código de Conduta",true);
        SubTarefa subTarefa2 = new SubTarefa(null,"Fazer curso de ética",true);
        SubTarefa subTarefa3 = new SubTarefa(null,"Ler normas de Segurança",false);
        List<SubTarefa> subTarefas = new ArrayList<>(List.of(subTarefa1, subTarefa2, subTarefa3));

        TarefaCadastroDTO tarefaCadastroDTO = new TarefaCadastroDTO("Ler as diretrizes da empresa",
                "Ler e compreender as diretrizes da empresa e entender seu papel como funcionário",
                true,
                LocalDateTime.now(),
                "888a08",
                valorPropriedadeTarefas,
                emProgresso,
                subTarefas,usuarioTarefaList);

        try {
            tarefaService.cadastrar(tarefaCadastroDTO,projetoRh.getId());
        } catch (DadosNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    private void criaTarefaEntregaDoModeloRelacionalBanco(Projeto projetoRh, Usuario usuario) {
        Status emProgresso = new Status();
        for (Status status: projetoRh.getStatusList()){
            if(status.getNome().equals("Em Progresso")){
                emProgresso = status;
            }
        }

        UsuarioTarefa usuarioTarefa = new UsuarioTarefa(null, usuario.getId());
        List<UsuarioTarefa> usuarioTarefaList = new ArrayList<>();
        usuarioTarefaList.add(usuarioTarefa);
        List<ValorPropriedadeTarefa> valorPropriedadeTarefas = new ArrayList<>();

        for (Propriedade propriedade: projetoRh.getPropriedades()){
            Data dataEntrega= new Data();
            dataEntrega.setData(LocalDateTime.of(LocalDate.of(124,6,3), LocalTime.of(13,40,0)));
            ValorPropriedadeTarefa valorPropriedadeTarefa = new ValorPropriedadeTarefa(null,propriedade,dataEntrega,true);
            valorPropriedadeTarefas.add(valorPropriedadeTarefa);
        }

        TarefaCadastroDTO tarefaCadastroDTO = new TarefaCadastroDTO("Fazer um modelo de banco de dados Relacional",
                "Este modelo de banco de dados será usado para guardar dados dos motores equipados com o sensor iot",
                true,
                LocalDateTime.now(),
                "8a0828",
                valorPropriedadeTarefas,
                emProgresso,
                null,usuarioTarefaList);

        try {
            tarefaService.cadastrar(tarefaCadastroDTO,projetoRh.getId());
        } catch (DadosNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }


    private void criaTarefaInterceptacaoRequestNode(Projeto projetoRh, Usuario usuario) {
        Status emProgresso = new Status();
        for (Status status: projetoRh.getStatusList()){
            if(status.getNome().equals("Em Progresso")){
                emProgresso = status;
            }
        }

        UsuarioTarefa usuarioTarefa = new UsuarioTarefa(null, usuario.getId());
        List<UsuarioTarefa> usuarioTarefaList = new ArrayList<>();
        usuarioTarefaList.add(usuarioTarefa);
        List<ValorPropriedadeTarefa> valorPropriedadeTarefas = new ArrayList<>();

        for (Propriedade propriedade: projetoRh.getPropriedades()){
            Data dataEntrega= new Data();
            dataEntrega.setData(LocalDateTime.of(LocalDate.of(124,6,12), LocalTime.of(13,40,0)));
            ValorPropriedadeTarefa valorPropriedadeTarefa = new ValorPropriedadeTarefa(null,propriedade,dataEntrega,true);
            valorPropriedadeTarefas.add(valorPropriedadeTarefa);
        }

        TarefaCadastroDTO tarefaCadastroDTO = new TarefaCadastroDTO("Desenvolver uma api em node.js para interceptação das request fornecidas pelo microcontrolador",
                "Este modelo de banco de dados será usado para guardar dados dos motores equipados com o sensor iot",
                true,
                LocalDateTime.now(),
                "046161",
                valorPropriedadeTarefas,
                emProgresso,
                null,usuarioTarefaList);

        try {
            tarefaService.cadastrar(tarefaCadastroDTO,projetoRh.getId());
        } catch (DadosNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    private void criaTarefaCriacaoBroker(Projeto projetoRh, Usuario usuario) {
        Status pronto = new Status();
        for (Status status: projetoRh.getStatusList()){
            if(status.getNome().equals("Pronto")){
                pronto = status;
            }
        }

        UsuarioTarefa usuarioTarefa = new UsuarioTarefa(null, usuario.getId());
        List<UsuarioTarefa> usuarioTarefaList = new ArrayList<>();
        usuarioTarefaList.add(usuarioTarefa);
        List<ValorPropriedadeTarefa> valorPropriedadeTarefas = new ArrayList<>();

        for (Propriedade propriedade: projetoRh.getPropriedades()){
            Data dataEntrega= new Data();
            dataEntrega.setData(LocalDateTime.of(LocalDate.of(124,6,4), LocalTime.of(13,40,0)));
            ValorPropriedadeTarefa valorPropriedadeTarefa = new ValorPropriedadeTarefa(null,propriedade,dataEntrega,true);
            valorPropriedadeTarefas.add(valorPropriedadeTarefa);
        }

        TarefaCadastroDTO tarefaCadastroDTO = new TarefaCadastroDTO("Criar um Broker Mqtt",
                "Este modelo de banco de dados será usado para guardar dados dos motores equipados com o sensor iot",
                true,
                LocalDateTime.now(),
                "520602",
                valorPropriedadeTarefas,
                pronto,
                null,usuarioTarefaList);

        try {
            tarefaService.cadastrar(tarefaCadastroDTO,projetoRh.getId());
        } catch (DadosNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }



    private void criaTarefaSelecaoCandidatosRh(Projeto projetoRh, Usuario usuario) {
        Status pronto = new Status();
        for (Status status: projetoRh.getStatusList()){
            if(status.getNome().equals("Pronto")){
                pronto = status;
            }
        }

        UsuarioTarefa usuarioTarefa = new UsuarioTarefa(null, usuario.getId());
        List<UsuarioTarefa> usuarioTarefaList = new ArrayList<>();
        usuarioTarefaList.add(usuarioTarefa);

        TarefaCadastroDTO tarefaCadastroDTO = new TarefaCadastroDTO("Seleção de Candidatos",
                "Selecionar os candidatos para a primeira fase do processo seletivo",
                true,
                LocalDateTime.now(),
                "5e1b6b",
                null,
                pronto,
                null,usuarioTarefaList);

        try {
            tarefaService.cadastrar(tarefaCadastroDTO,projetoRh.getId());
        } catch (DadosNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    private void criaTarefaProvasRh(Projeto projetoRh, Usuario usuario) {
        Status pronto = new Status();
        for (Status status: projetoRh.getStatusList()){
            if(status.getNome().equals("Pronto")){
                pronto = status;
            }
        }

        UsuarioTarefa usuarioTarefa = new UsuarioTarefa(null, usuario.getId());
        List<UsuarioTarefa> usuarioTarefaList = new ArrayList<>();
        usuarioTarefaList.add(usuarioTarefa);

        TarefaCadastroDTO tarefaCadastroDTO = new TarefaCadastroDTO("Provas",
                "Aplicar provas de raciocínio lógico, matemática básica e concentração, com o resultado das provas selecionar os canditados que vão para a entrevista",
                true,
                LocalDateTime.now(),
                "1b3e6b",
                null,
                pronto,
                null,usuarioTarefaList);

        try {
            tarefaService.cadastrar(tarefaCadastroDTO,projetoRh.getId());
        } catch (DadosNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
    }

    private void criaTarefaEntrevistasRh(Projeto projetoRh, Usuario antonio, Usuario lucas) throws IOException {
        Status emProgresso = new Status();
        for (Status status: projetoRh.getStatusList()){
            if(status.getNome().equals("Em Progresso")){
                emProgresso = status;
            }
        }

        UsuarioTarefa usuarioTarefa = new UsuarioTarefa(null, antonio.getId());
        UsuarioTarefa usuarioTarefaLucas = new UsuarioTarefa(null, lucas.getId());

        List<UsuarioTarefa> usuarioTarefaList = new ArrayList<>();
        usuarioTarefaList.add(usuarioTarefa);
        usuarioTarefaList.add(usuarioTarefaLucas);

        TarefaCadastroDTO tarefaCadastroDTO = new TarefaCadastroDTO("Entrevistas",
                "Entrevistar os candidatos finais e avaliar a perspectiva de futuro dos mesmos",
                true,
                LocalDateTime.now(),
                "1e4f08",
                null,
                emProgresso,
                null,usuarioTarefaList);

        Tarefa tarefa=null;
        try {
           tarefa= tarefaService.cadastrar(tarefaCadastroDTO,projetoRh.getId());
        } catch (DadosNaoEncontradoException e) {
            throw new RuntimeException(e);
        }

        ClassPathResource resource = new ClassPathResource ("AprovadosCentroWeg.pdf");
        byte[] content = Files.readAllBytes(resource.getFile().toPath());
        Arquivo result = new Arquivo("AprovadosCentroWeg.pdf", content,"application/pdf");
        try {
            tarefaService.atualizarArquivo(tarefa.getId(),result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Projeto criProjetoPadraoTi(Equipe equipe, List<UsuarioProjeto> responsaveis) {
        List<Status> statusPadrao = new ArrayList<>();
        Status naoIniciado = new Status("Não iniciado", "36213E");
        Status pronto = new Status("Pronto", "38a31a");
        Status emProgresso = new Status("Em Progresso", "17179c");
        statusPadrao.add(pronto);
        statusPadrao.add(emProgresso);
        statusPadrao.add(naoIniciado);
        List<Propriedade> propriedades = new ArrayList<>();
        Propriedade propriedade = new Propriedade(null,"Data de entrega",Tipo.DATA);
        propriedades.add(propriedade);
        List<ProjetoEquipe> projetoEquipes = new ArrayList<>();
        ProjetoEquipe projetoEquipeRh = new ProjetoEquipe(equipe);
        projetoEquipes.add(projetoEquipeRh);
        LocalDate dataFinal= null;
        ProjetoCadastroDTO projetoCadastroDTO = new ProjetoCadastroDTO("Desenvolvimento WEGnology",
                "Desenvolver aplicações para monitoramento de motores na fábrica",
                LocalDateTime.now(),
                statusPadrao,new ArrayList<>(),propriedades,projetoEquipes, responsaveis,LocalDate.of(124,6,6),"meus-projetos");

        try {
            return projetoService.cadastrar(projetoCadastroDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Projeto criaProjetoPadraoRh(Equipe equipe, List<UsuarioProjeto> responsaveis) {
        List<Status> statusPadrao = new ArrayList<>();
        Status naoIniciado = new Status("Não iniciado", "36213E");
        Status pronto = new Status("Pronto", "38a31a");
        Status emProgresso = new Status("Em Progresso", "17179c");
        statusPadrao.add(pronto);
        statusPadrao.add(emProgresso);
        statusPadrao.add(naoIniciado);
        List<ProjetoEquipe> projetoEquipes = new ArrayList<>();
        ProjetoEquipe projetoEquipeRh = new ProjetoEquipe(equipe);
        projetoEquipes.add(projetoEquipeRh);
        LocalDate dataFinal= null;
        ProjetoCadastroDTO projetoCadastroDTO = new ProjetoCadastroDTO("Seleção CTW","Fazer o Processo seletivo para nova turma CTW",LocalDateTime.now(),
                statusPadrao,new ArrayList<>(),new ArrayList<>(),projetoEquipes, responsaveis,LocalDate.of(2024,6,6),"meus-projetos");

        try {
            return projetoService.cadastrar(projetoCadastroDTO);
        } catch (IOException e) {
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
