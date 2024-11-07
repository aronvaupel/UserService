package com.ecommercedemo.userservice.controller

import com.ecommercedemo.common.model.CustomProperty
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

    @PostMapping("/custom-property/user")
    fun createUserCustomProperty(
        @RequestParam userId: UUID,
        @RequestBody customProperty: UserServicePseudoProperty
    ): ResponseEntity<CustomProperty> {
        val createdProperty = pseudoPropertyService.addCustomPropertyToAllUsers(customProperty)
        return ResponseEntity.ok(createdProperty)
    }

    @PostMapping("/custom-property/contact-data")
    fun createContactDataCustomProperty(
        @RequestParam contactDataId: UUID,
        @RequestBody customProperty: UserServicePseudoProperty
    ): ResponseEntity<CustomProperty> {
        val createdProperty = pseudoPropertyService.addCustomPropertyToAllContactData(customProperty)
        return ResponseEntity.ok(createdProperty)
    }

    @PutMapping("/custom-property/user/{key}")
    fun renameUserCustomProperty(
        @PathVariable key: String,
        @RequestParam newKey: String
    ): ResponseEntity<UserServicePseudoProperty> {
        return ResponseEntity.ok(pseudoPropertyService.renameCustomProperty("ContactData", key, newKey))
    }

    @PutMapping("/custom-property/contact-data/key/{key}")
    fun renameContactDataCustomProperty(
        @PathVariable key: String,
        @RequestParam newKey: String
    ): ResponseEntity<UserServicePseudoProperty> {
        return ResponseEntity.ok(pseudoPropertyService.renameCustomProperty("ContactData", key, newKey))
    }

    @DeleteMapping("/custom-property/user/{key}")
    fun deleteUserCustomProperty(
        @PathVariable key: String
    ): ResponseEntity<Void> {
        pseudoPropertyService.deleteCustomPropertyByEntityAndKey(User::class.simpleName!!, key)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/custom-property/contact-data/{key}")
    fun deleteContactDataCustomProperty(
        @PathVariable key: String
    ): ResponseEntity<Void> {
        pseudoPropertyService.deleteCustomPropertyByEntityAndKey(UserInfo::class.simpleName!!, key)
        return ResponseEntity.noContent().build()
    }
}