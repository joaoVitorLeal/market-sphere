package io.github.joaoVitorLeal.marketsphere.billing.bucket;

import io.github.joaoVitorLeal.marketsphere.billing.bucket.exception.StorageAccessException;
import io.github.joaoVitorLeal.marketsphere.billing.config.props.MinioProps;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BucketService {

    private final MinioClient minioClient;
    private final MinioProps minioProps;

    public BucketService(MinioClient minioClient, MinioProps minioProps) {
        this.minioClient = minioClient;
        this.minioProps = minioProps;
    }

    /**
     * Realizar upload de arquivos para o Cloud bucket
     * */
    public void upload(BucketFile file) {
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(minioProps.getBucketName())
                    .object(file.name())
                    .stream(file.inputStream(), file.size(), -1)
                    .contentType(file.mediaType().toString())
                    .build();
            minioClient.putObject(objectArgs); // Adicionar objetos ao bucket
        } catch (Exception e) {
            throw new StorageAccessException("Failed to upload file: " + file.name(), e);
        }
    }

    /**
     *  Retorna a url para se obter o arquivo
     * */
    public String generatePresignedUrl(String fileName) {
        final int expiryTimeInDays = 5;
        try {
            GetPresignedObjectUrlArgs presignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(minioProps.getBucketName())
                    .object(fileName)
                    .expiry(expiryTimeInDays, TimeUnit.DAYS)
                    .build();
           return minioClient.getPresignedObjectUrl(presignedObjectUrlArgs);
        } catch (Exception e) {
            throw new StorageAccessException("Failed to generate presigned URL for file: " + fileName, e);
        }
    }
}
