package dev.hypestsoftware.hackyeah2020.backend.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "roles")
open class Role(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    open val uuid: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    open var name: RoleName,

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    open var user: User
)
