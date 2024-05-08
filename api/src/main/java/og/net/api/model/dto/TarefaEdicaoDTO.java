package og.net.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import og.net.api.model.entity.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TarefaEdicaoDTO implements IDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
    private String cor;
    private List<ValorPropriedadeTarefa> valorPropriedadeTarefas;
    private Status status;
    private List<Comentario> comentarios;
    private List<SubTarefa> subTarefas;
    private List<Arquivo> arquivos;
    private List<Indice> indice;


    public void setArquivos(List<MultipartFile> listaDeArquivosRecebidos) throws IOException {
        List<Arquivo> listaDeArquivosTemporaria = new ArrayList<Arquivo>();
        listaDeArquivosTemporaria.stream().forEach(arquivoTemporario-> listaDeArquivosRecebidos.stream().forEach(arquivoRecebido -> {
            try {
                arquivoTemporario.setDados(arquivoRecebido.getBytes());
                arquivoTemporario.setNome(arquivoRecebido.getOriginalFilename());
                arquivoTemporario.setTipo(arquivoRecebido.getContentType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        this.arquivos = listaDeArquivosTemporaria;
    }
}
