package dev.hypestsoftware.hackyeah2020.backend.model

import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
open class User(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    open val uuid: UUID = UUID.randomUUID(),

    @Column(unique = true, nullable = false)
    open var username: String,

    @Column(nullable = false)
    open var password: String,

    @Column
    open var enabled: Boolean,

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    open var roles: MutableSet<Role> = mutableSetOf(),
)
