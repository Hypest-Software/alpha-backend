package dev.hypestsoftware.hackyeah2020.backend.repository

import dev.hypestsoftware.hackyeah2020.backend.model.Role
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : CrudRepository<Role, UUID>
