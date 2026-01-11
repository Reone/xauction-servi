package com.reone.xauction.config

import io.minio.MinioClient
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(MinioProperties::class)
class MinioConfig(private val minioProperties: MinioProperties) {

    @Bean
    fun minioClient(): MinioClient {
        return MinioClient.builder()
            .endpoint(minioProperties.resolvedApiEndpoint())
            .credentials(minioProperties.accessKey, minioProperties.secretKey)
            .build()
    }
}
