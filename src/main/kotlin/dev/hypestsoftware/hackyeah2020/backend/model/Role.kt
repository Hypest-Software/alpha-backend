package dev.hypestsoftware.hackyeah2020.backend.model

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

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
