package com.kakaopaysec.upload.file

import com.kakaopaysec.upload.domain.UploadFile
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Component
class FileStore {

    @Value("\${file.dir}")
    lateinit var fileDir: String

    fun getFullPath(fileName: String): String {
        return fileDir + fileName
    }

    fun storeFiles(multipartFiles: MutableList<MultipartFile>?): MutableList<UploadFile?> {

        val storeFileResult = mutableListOf<UploadFile?>()

        multipartFiles?.forEach {
            if (!it.isEmpty) {
                storeFileResult.add(storeFile(it))
            }
        }

        return storeFileResult
    }

    fun storeFile(multipartFile: MultipartFile?): UploadFile? {
        multipartFile?.let {

            if (multipartFile.isEmpty) {
                return null
            }

            val originalFilename = multipartFile.originalFilename
            val storeFileName = createStoreFileName(originalFilename)
            multipartFile.transferTo(File(getFullPath(storeFileName)))
            return UploadFile(originalFilename, storeFileName)
        } ?: return null
    }

    private fun createStoreFileName(originalFilename: String?): String {
        val uuid = UUID.randomUUID().toString()
        val ext = originalFilename?.substringAfter(".")
        return "$uuid.$ext"
    }
}
