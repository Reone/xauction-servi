package com.reone.xauction.service

import org.springframework.web.multipart.MultipartFile

interface StorageService {
    fun uploadAuctionImages(files: List<MultipartFile>, folder: String = "auction"): List<String>
}
