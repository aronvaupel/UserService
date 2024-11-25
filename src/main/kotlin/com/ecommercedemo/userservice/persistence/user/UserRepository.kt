package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.persistence.abstraction.EntityRepository
import com.ecommercedemo.userservice.model.user.User
import java.util.*

interface UserRepository : EntityRepository<User, UUID>