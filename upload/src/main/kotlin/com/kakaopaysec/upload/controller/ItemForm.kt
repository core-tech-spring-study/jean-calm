package com.kakaopaysec.upload.controller

import org.springframework.web.multipart.MultipartFile

data class ItemForm(
    val itemId: Long?,
    val itemName: String?,
    val attachFile: MultipartFile?,
    val imageFiles: MutableList<MultipartFile>?
)
