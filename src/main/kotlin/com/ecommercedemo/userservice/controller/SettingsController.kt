package com.ecommercedemo.userservice.controller

import com.ecommercedemo.common.model.PseudoProperty
import com.ecommercedemo.userservice.model.pseudoproperty.UserServicePseudoProperty
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.model.userinfo.UserInfo
import com.ecommercedemo.userservice.service.pseudoproperty.PseudoPropertyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/settings")
class SettingsController(
    private val pseudoPropertyService: PseudoPropertyService,
) {

    @PostMapping("/pseudo-property/user")
    fun createUserPseudoProperty(
        @RequestParam userId: UUID,
        @RequestBody pseudoProperty: UserServicePseudoProperty
    ): ResponseEntity<PseudoProperty> {
        val createdProperty = pseudoPropertyService.addPseudoPropertyToAllUsers(pseudoProperty)
        return ResponseEntity.ok(createdProperty)
    }

    @PostMapping("/pseudo-property/user-info")
    fun createUserInfoPseudoProperty(
        @RequestParam userInfoId: UUID,
        @RequestBody pseudoProperty: UserServicePseudoProperty
    ): ResponseEntity<PseudoProperty> {
        val createdProperty = pseudoPropertyService.addPseudoPropertyToAllUserInfo(pseudoProperty)
        return ResponseEntity.ok(createdProperty)
    }

    @PutMapping("/pseudo-property/user/{key}")
    fun renameUserPseudoProperty(
        @PathVariable key: String,
        @RequestParam newKey: String
    ): ResponseEntity<UserServicePseudoProperty> {
        return ResponseEntity.ok(pseudoPropertyService.renamePseudoProperty("UserInfo", key, newKey))
    }

    @PutMapping("/pseudo-property/user-info/key/{key}")
    fun renameUserInfoPseudoProperty(
        @PathVariable key: String,
        @RequestParam newKey: String
    ): ResponseEntity<UserServicePseudoProperty> {
        return ResponseEntity.ok(pseudoPropertyService.renamePseudoProperty("UserInfo", key, newKey))
    }

    @DeleteMapping("/pseudo-property/user/{key}")
    fun deleteUserPseudoProperty(
        @PathVariable key: String
    ): ResponseEntity<Void> {
        pseudoPropertyService.deletePseudoPropertyByEntityAndKey(User::class.simpleName!!, key)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/pseudo-property/user-info/{key}")
    fun deleteUserInfoPseudoProperty(
        @PathVariable key: String
    ): ResponseEntity<Void> {
        pseudoPropertyService.deletePseudoPropertyByEntityAndKey(UserInfo::class.simpleName!!, key)
        return ResponseEntity.noContent().build()
    }
}