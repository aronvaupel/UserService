package com.ecommercedemo.userservice.controller

import com.ecommercedemo.userservice.dto.user.UserRegisterDto
import com.ecommercedemo.userservice.dto.user.UserResponseDto
import com.ecommercedemo.userservice.dto.user.UserUpdateDto
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.service.user.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/users")
@Validated
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getUsers(
        @RequestParam(required = true) ids: List<UUID>,
    ): ResponseEntity<List<User>> {
        val users = userService.getUsers(ids)
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable id: UUID
    ): ResponseEntity<User> {
        return ResponseEntity.ok(userService.getUser(id))
    }

    @PostMapping
    fun registerUser(
        @RequestBody @Valid dto: UserRegisterDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(
            userService.registerUser(dto)
        )
    }

    @PutMapping
    fun updateUser(
        @RequestBody dto: UserUpdateDto
    ): ResponseEntity<UserResponseDto> {
        return ResponseEntity.ok(
            userService.updateUser(dto)
        )
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/import")
    fun importUsersFromExcel(
        @RequestParam("file") file: MultipartFile,
        @RequestParam sheet: String,
        @RequestParam rows: Int,
        @RequestParam evaluationColumn: String
    ): ResponseEntity<Void> {
        userService.importUsersFromExcel(file, sheet, rows, evaluationColumn)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/export/excel")
    fun exportUsersToExcel(
        @RequestParam(required = false) filter: String?,
        @RequestParam(required = false) userInfoProperties: List<String>?
    ): ResponseEntity<ByteArray> {
        val excelData = userService.exportUsersToExcel(filter, userInfoProperties)
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=users.xlsx")
            .body(excelData)
    }

    @GetMapping("/export/pdf")
    fun exportUsersToPdf(@RequestParam(required = false) filter: String?): ResponseEntity<ByteArray> {
        val pdfData = userService.exportUsersToPdf(filter)
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=users.pdf")
            .body(pdfData)
    }

}