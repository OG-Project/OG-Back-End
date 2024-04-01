package og.net.api.service;

import lombok.AllArgsConstructor;
import og.net.api.exception.TarefaInesxistenteException;
import og.net.api.model.entity.Arquivo;
import og.net.api.model.entity.Imagem;
import og.net.api.model.entity.Tarefa;
import og.net.api.model.entity.Usuario;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ImagemService {

    private Environment env;
    private UsuarioService usuarioService;

    public boolean enviaFoto(MultipartFile file,Integer id) throws IOException {
        String awsKeyId = env.getProperty("keyId");
        String awsKeySecret = env.getProperty("keySecret");
        String region = "us-east-1";
        String bucketName = env.getProperty("bucketName");
        Imagem imagem = new Imagem(file);
        String awsChave = UUID.randomUUID().toString();
        imagem.setChaveAws(awsChave);
        usuarioService.atualizarFoto(id,imagem);
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(awsKeyId, awsKeySecret);

        try (S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(region))
                .build()) {

            if (!doesBucketExist(s3Client, bucketName)) {
                return false;
            }

            String contentType = file.getContentType();

            try (InputStream fileInputStream = file.getInputStream()) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(awsChave)
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
    public String getFoto(String keyName) {
        String awsKeyId = env.getProperty("keyId");
        String awsKeySecret = env.getProperty("keySecret");
        String bucketName = env.getProperty("bucketName");
        S3Client s3 = S3Client.builder().region(Region.US_EAST_1).build();
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(awsKeyId, awsKeySecret);

        try (S3Presigner presigner = S3Presigner.builder().region(Region.US_EAST_1).credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).build()) {

            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofDays(6))  // The URL will expire in 10 minutes.
                    .getObjectRequest(objectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);

            return presignedRequest.url().toExternalForm();
        }

    }
}
