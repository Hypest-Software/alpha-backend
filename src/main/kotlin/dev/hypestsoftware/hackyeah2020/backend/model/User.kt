package dev.hypestsoftware.hackyeah2020.backend.model

import dev.hypestsoftware.hackyeah2020.backend.model.dto.UserDto
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    val uuid: UUID = UUID.randomUUID(),

    @Column(unique = true, nullable = false)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @Column
    var enabled: Boolean,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_uuid")],
        inverseJoinColumns = [JoinColumn(name = "role_uuid")]
    )
    var roles: MutableSet<Role> = mutableSetOf(),
) {
    fun toUserDto() = UserDto(uuid.toString(), username, roles.map { role -> role.name.toString() }.toSet())

    fun toCurrentUserDto() = UserDto(uuid.toString(), username, roles.map { role -> role.name.toString() }.toSet())
}
