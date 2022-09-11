package com.kakaopaysec.upload.domain

data class Item(
    val itemName: String?,
    val attachFile: UploadFile?,
    val imageFiles: MutableList<UploadFile?>
) {
    var id: Long? = null
}
