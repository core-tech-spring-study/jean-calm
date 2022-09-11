package com.kakaopaysec.upload.controller

import com.kakaopaysec.upload.domain.Item
import com.kakaopaysec.upload.domain.repository.ItemRepository
import com.kakaopaysec.upload.file.FileStore
import mu.KotlinLogging
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.util.UriUtils
import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets

private val logger = KotlinLogging.logger {}

@Controller
class ItemController(
    private val itemRepository: ItemRepository,
    private val fileStore: FileStore
) {

    @GetMapping("/items/new")
    fun newItem(@ModelAttribute form: ItemForm): String {
        return "item-form"
    }

    @PostMapping("/items/new")
    fun saveItem(@ModelAttribute form: ItemForm, redirectAttributes: RedirectAttributes): String {

        val attachFile = fileStore.storeFile(form.attachFile)
        val storeImageFiles = fileStore.storeFiles(form.imageFiles)

        val item = Item(form.itemName, attachFile, storeImageFiles)
        itemRepository.save(item)

        redirectAttributes.addAttribute("itemId", item.id)

        return "redirect:/items/{itemId}"
    }

    @GetMapping("/items/{id}")
    fun items(@PathVariable id: Long, model: Model): String {
        val item = itemRepository.findById(id)
        model.addAttribute("item", item)
        return "item-view"
    }

    @ResponseBody
    @GetMapping("/images/{fileName}")
    fun downloadImage(@PathVariable fileName: String): Resource {
        return UrlResource("file:${fileStore.getFullPath(fileName)}")
    }

    @GetMapping("/attach/{itemId}")
    fun downloadAttach(@PathVariable itemId: Long): ResponseEntity<Resource> {
        val item = itemRepository.findById(itemId)
        item?.let {
            val storeFileName = it.attachFile?.storeFileName
            val uploadFileName = it.attachFile?.uploadFileName
            val urlResource = UrlResource("file:${fileStore.getFullPath(storeFileName!!)}")
            logger.info { "uploadFileName = $uploadFileName" }

            val encodedUploadFileName = UriUtils.encode(uploadFileName!!, StandardCharsets.UTF_8)
            val contentDisposition = """attachment; filename="$encodedUploadFileName""""

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(urlResource)
        } ?: kotlin.run {
            throw FileNotFoundException("해당 파일이 존재하지 않습니다. itemId = $itemId")
        }
    }
}
