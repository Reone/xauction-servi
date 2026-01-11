package com.reone.xauction.service.impl

import com.reone.xauction.config.MinioProperties
import com.reone.xauction.service.StorageService
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID
import javax.annotation.PostConstruct

@Service
class MinioStorageService(
    private val minioClient: MinioClient,
    private val minioProperties: MinioProperties,
) : StorageService {

    @PostConstruct
    fun initBucket() {
        ensureBucket()
    }

    override fun uploadAuctionImages(files: List<MultipartFile>, folder: String): List<String> {
        val validFiles = files.filter { !it.isEmpty }
        if (validFiles.isEmpty()) {
            throw IllegalArgumentException("请至少上传一张图片")
        }
        ensureBucket()
        return validFiles.map { file ->
            uploadSingleFile(file, folder)
        }
    }

    private fun uploadSingleFile(file: MultipartFile, folder: String): String {
        val objectName = buildObjectName(folder, file.originalFilename)
        file.inputStream.use { inputStream ->
            val putArgs = PutObjectArgs.builder()
                .bucket(minioProperties.bucket)
                .`object`(objectName)
                .contentType(file.contentType ?: "application/octet-stream")
                .stream(inputStream, file.size, -1)
                .build()
            minioClient.putObject(putArgs)
        }
        return buildPublicUrl(objectName)
    }

    private fun buildObjectName(folder: String, filename: String?): String {
        val baseFolder = folder.trim('/').ifEmpty { "auction" }
        val safeName = filename?.replace("\\s+".toRegex(), "-") ?: "image"
        return "$baseFolder/${UUID.randomUUID()}-$safeName"
    }

    private fun buildPublicUrl(objectName: String): String {
        val baseUrl = (minioProperties.publicUrl ?: minioProperties.endpoint).trimEnd('/')
        return "$baseUrl/${minioProperties.bucket}/$objectName"
    }

    private fun ensureBucket() {
        val bucketName = minioProperties.bucket
        if (bucketName.isBlank()) {
            throw IllegalStateException("未配置 MinIO bucket")
        }
        val bucketExists = minioClient.bucketExists(
            BucketExistsArgs.builder().bucket(bucketName).build()
        )
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build())
        }
    }
}
