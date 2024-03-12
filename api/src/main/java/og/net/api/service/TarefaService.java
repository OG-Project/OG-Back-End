package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.DadosNaoEncontradoException;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.exception.TarefaJaExistenteException;
import og.net.api.model.dto.IDTO;
import og.net.api.model.dto.TarefaCadastroDTO;
import og.net.api.model.dto.TarefaEdicaoDTO;
import og.net.api.model.entity.Arquivo;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.Usuario;
import og.net.api.repository.TarefaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.servicemetadata.MemoryDbServiceMetadata;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class TarefaService {

    private TarefaRepository tarefaRepository;
    private Environment env;

    public boolean uploadFile(MultipartFile file) {
         String awsKeyId = "AKIA5RRHCKYZVQT3ALJY";
         String awsKeySecret = "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";
         String region = "us-east-1";
         String bucketName = "bucket-imagens-projeto";
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(awsKeyId, awsKeySecret);

        try (S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(region))
                .build()) {

            if (!doesBucketExist(s3Client, bucketName)) {
                return false;
            }
            String idAws= UUID.randomUUID().toString();

            String fileKey = file.getOriginalFilename(); // Assumindo que você deseja usar o nome original do arquivo como chave
            String contentType = file.getContentType();

            try (InputStream fileInputStream = file.getInputStream()) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(idAws)
                        .contentType(contentType)
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(fileInputStream, file.getSize()));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean doesBucketExist(S3Client s3Client, String bucketName) {
        try {
            s3Client.headBucket(b -> b.bucket(bucketName));
            return true;
        } catch (S3Exception e) {
            return false;
        }
    }

    public Tarefa buscarUm(Integer id) throws TarefaInesxistenteException {
        if (tarefaRepository.existsById(id)){
            return tarefaRepository.findById(id).get();

        }
        throw new TarefaInesxistenteException();
    }

    public List<Tarefa> buscarTarefasNome(String nome){
        return tarefaRepository.findByNome(nome);
    }

    public List<Tarefa> buscarTodos(){
        return tarefaRepository.findAll();
    }
    public Page<Tarefa> buscarTodos(Pageable pageable){
        return tarefaRepository.findAll(pageable);
    }

    public void deletar(Integer id){
        tarefaRepository.deleteById(id);
    }

    public void cadastrar(IDTO dto) {
        TarefaCadastroDTO tarefaCadastroDTO = (TarefaCadastroDTO) dto;
        System.out.println(dto);
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaCadastroDTO,tarefa);
        tarefaRepository.save(tarefa);


    }



    public void atualizarFoto(Integer id, List<MultipartFile> arquivos) throws IOException, TarefaInesxistenteException {
        Tarefa tarefa = buscarUm(id);
        ArrayList<Arquivo> arquivosTeste = new ArrayList<>() ;
        arquivos.stream().forEach(arquivo->{
            try {
                arquivosTeste.add(new Arquivo(arquivo));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        tarefa.setArquivos(arquivosTeste);
        tarefaRepository.save(tarefa);
    }

    public Tarefa editar(IDTO dto) throws DadosNaoEncontradoException {
        TarefaEdicaoDTO tarefaEdicaoDTO = (TarefaEdicaoDTO) dto;
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(tarefaEdicaoDTO,tarefa);
        if (tarefaRepository.existsById(tarefa.getId())){
            tarefaRepository.save(tarefa);
            return tarefa;
        }
        throw new DadosNaoEncontradoException();

    }
}
