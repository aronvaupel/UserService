package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.persistence.abstraction.PostgresEntityPersistenceAdapter
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service

@Service
class PostgresUserPersistenceAdapter(
    repository: UserRepository
) : PostgresEntityPersistenceAdapter<User>(repository)

