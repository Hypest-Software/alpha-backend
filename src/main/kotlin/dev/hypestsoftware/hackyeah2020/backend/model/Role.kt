package dev.hypestsoftware.hackyeah2020.backend.model

import javax.persistence.*

@Entity
@Table(name = "roles")
class Role(
    @Id
    @GeneratedValue
    val uuid: Long = 0L,

    @Enumerated(EnumType.STRING)
    var name: RoleName,

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
)
