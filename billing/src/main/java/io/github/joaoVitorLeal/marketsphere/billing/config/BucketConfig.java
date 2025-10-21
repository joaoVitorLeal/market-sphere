package io.github.joaoVitorLeal.marketsphere.billing.config;

import io.github.joaoVitorLeal.marketsphere.billing.config.props.MinioProps;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BucketConfig {

    private final MinioProps minioProps;

    public BucketConfig(MinioProps minioProps) {
        this.minioProps = minioProps;
    }

    @Bean
    public MinioClient bucketClient() {
        return MinioClient.builder()
                .endpoint(minioProps.getUrl())
                .credentials(minioProps.getAccessKey(), minioProps.getSecretKey())
                .build();
    }
}
