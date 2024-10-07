package com.ecommercedemo.userservice.persistence.contactdata

import com.ecommercedemo.userservice.model.contactdata.ContactData
import org.springframework.stereotype.Service

@Service
class ContactDataAdapterPostgresql(
    private val contactDataRepository: ContactDataRepository
) : IContactDataAdapter {
    override fun saveContactData(data: ContactData) {
        TODO("Not yet implemented")
    }

    override fun getContactDataById() {
        TODO("Not yet implemented")
    }

    override fun updateContactData() {
        TODO("Not yet implemented")
    }

    override fun deleteContactData() {
        TODO("Not yet implemented")
    }

    override fun existsByMail(email: String): Boolean {
        TODO("Not yet implemented")
    }
}