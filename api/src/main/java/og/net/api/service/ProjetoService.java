package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.EquipeNaoEncontradaException;
import og.net.api.exception.ProjetoNaoEncontradoException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.ProjetoCadastroDTO;
import og.net.api.model.dto.ProjetoEdicaoDTO;
import og.net.api.model.dto.PropriedadeCadastroDTO;
import og.net.api.model.entity.*;
import og.net.api.repository.ProjetoEquipeRepository;
import og.net.api.repository.ProjetoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjetoService {


    private ProjetoRepository projetoRepository;
    private EquipeService equipeService;
    private ProjetoEquipeRepository projetoEquipeRepository;
    private PropriedadeService propriedadeService;



    public Projeto buscarUm(Integer id) throws ProjetoNaoEncontradoException {
        if (projetoRepository.existsById(id)){
           return projetoRepository.findById(id).get();
        }
        throw new ProjetoNaoEncontradoException();
    }

    public List<Projeto> buscarProjetosNome(String nome){
        return projetoRepository.findByNome(nome);
    }

    public List<Projeto> buscarTodos(){
        return projetoRepository.findAll();
    }


    public void deletar(Integer id){
        projetoRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto) {
        ProjetoCadastroDTO projetoCadastroDTO = (ProjetoCadastroDTO) dto;
        Projeto projeto = new Projeto();
        BeanUtils.copyProperties(projetoCadastroDTO, projeto);
        criaValorPorpiredadeTarefa(projeto);
        projetoRepository.save(projeto);
    }

    public Projeto editar(IDTO dto) throws DadosNaoEncontradoException {
        ProjetoEdicaoDTO projetoEdicaoDTO = (ProjetoEdicaoDTO) dto;
        Projeto projeto = new Projeto();
        BeanUtils.copyProperties(projetoEdicaoDTO,projeto);
        if (projetoRepository.existsById(projeto.getId())){
            criaValorPorpiredadeTarefa(projeto);
            projetoRepository.save(projeto);
             return projeto;
        }
        throw new DadosNaoEncontradoException();
    }

    public void criaValorPorpiredadeTarefa(Projeto projeto){
        projeto.getPropriedades().forEach(propriedade -> {
            if (propriedade.getId()==null){
                PropriedadeCadastroDTO propriedadeCadastroDTO = new PropriedadeCadastroDTO(propriedade);
                try {
                    propriedadeService.cadastrar(propriedadeCadastroDTO,projeto.getId());
                } catch (ProjetoNaoEncontradoException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void adicionarAProjeto(Integer projetoId, List<Integer> ids) throws ProjetoNaoEncontradoException {
        System.out.println(buscarUm(projetoId));
            Projeto projeto = buscarUm(projetoId);
            ids.forEach(id -> {
                try {
                    Equipe equipe = equipeService.buscarUm(id);
                    ProjetoEquipe projetoEquipe = new ProjetoEquipe();
                    if (projetoEquipe.getEquipes() == null) projetoEquipe.setEquipes(List.of(equipe));
                    else projetoEquipe.getEquipes().add(equipe);
                    //setar as permissões
                    projeto.getProjetosEquipes().add(projetoEquipe);
                    projetoRepository.save(projeto);
                } catch (Exception ignored) {}
            });
    }

    public List<Projeto> buscarProjetosEquipes(Integer equipeId) throws EquipeNaoEncontradaException {
        Equipe equipe = equipeService.buscarUm(equipeId);
        return projetoEquipeRepository.findAllByEquipes(equipe).stream().map(
                eu -> projetoRepository.findByProjetosEquipesContaining(eu)).toList();
    }

    
}
