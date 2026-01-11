package com.reone.xauction.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "minio")
data class MinioProperties(
    /** 对外访问域名或控制台地址 */
    var endpoint: String = "",
    /** S3 API 地址（为空时默认使用 endpoint） */
    var apiEndpoint: String? = null,
    var accessKey: String = "",
    var secretKey: String = "",
    var bucket: String = "",
    var publicUrl: String? = null,
) {
    fun resolvedApiEndpoint(): String = (apiEndpoint?.takeIf { it.isNotBlank() } ?: endpoint).trimEnd('/')
}
