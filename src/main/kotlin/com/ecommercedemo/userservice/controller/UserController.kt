package com.ecommercedemo.userservice.controller

import com.ecommercedemo.userservice.dto.user.RegisterUserDto
import com.ecommercedemo.userservice.dto.user.UserDto
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/users")
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
    fun getUserById(@PathVariable id: UUID): ResponseEntity<User> {
        val user = userService.getUser(id)
        return ResponseEntity.ok(user)
    }

    @PostMapping
    fun registerUser(
        @RequestBody dto: RegisterUserDto
    ): ResponseEntity<UserDto> {
        return ResponseEntity.ok(
            userService.registerUser(dto)
        )
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<User> {
        val updatedUser = userService.updateUser(user)
        return ResponseEntity.ok(updatedUser)
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
        @RequestParam(required = false) contactDataProperties: List<String>?
    ): ResponseEntity<ByteArray> {
        val excelData = userService.exportUsersToExcel(filter, contactDataProperties)
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