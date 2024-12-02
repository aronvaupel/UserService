package com.ecommercedemo.userservice.controller

import com.ecommercedemo.common.controller.abstraction.RestControllerTemplate
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.service.user.UserRestService
import org.springframework.context.annotation.DependsOn
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/users")
@Validated
@DependsOn("userRestService")
class UserController(
    private val service: UserRestService,
) : RestControllerTemplate<User>(service) {

    init {
        println("UserRestService injected: $service") // Should not be null
    }

    @PostMapping("/import")
    fun importUsersFromExcel(
        @RequestParam("file") file: MultipartFile,
        @RequestParam sheet: String,
        @RequestParam rows: Int,
        @RequestParam evaluationColumn: String
    ): ResponseEntity<Void> {
        service.importUsersFromExcel(file, sheet, rows, evaluationColumn)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/export/excel")
    fun exportUsersToExcel(
        @RequestParam(required = false) filter: String?,
        @RequestParam(required = false) userInfoProperties: List<String>?
    ): ResponseEntity<ByteArray> {
        val excelData = service.exportUsersToExcel(filter, userInfoProperties)
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=users.xlsx")
            .body(excelData)
    }

    @GetMapping("/export/pdf")
    fun exportUsersToPdf(@RequestParam(required = false) filter: String?): ResponseEntity<ByteArray> {
        val pdfData = service.exportUsersToPdf(filter)
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=users.pdf")
            .body(pdfData)
    }

}